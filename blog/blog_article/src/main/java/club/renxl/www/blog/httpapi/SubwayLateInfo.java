package club.renxl.www.blog.httpapi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.jms.Queue;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.renxl.www.blog.article.domain.WanDianInfoDto;
import club.renxl.www.blog.client.dao.RedisDao;
import club.renxl.www.response.BaseResponse;

/**
 * 	乱入api。提供给我的母亲使用
 *	晚点信息查询优化
 *	通过生产-消费模式监控调度
 *	redis存储最后的调度时间
 * @author renxl
 * @date 2018/12/24
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("http")

public class SubwayLateInfo {
	
	private static Logger log = LoggerFactory.getLogger(SubwayLateInfo.class);
	@Autowired
    private JmsMessagingTemplate jms;

    @Autowired
    private Queue queue;

//    @Autowired
//    private Topic topic;
    
	/**
	 * jackson工具
	 */
    private static final ObjectMapper mapper = new ObjectMapper();
	/**
	 *	单位:  秒  25分钟 
	 *  redis key的过期时间;本来想缓存所有数据，后期可以抽取做一个数据分析
	 *  key一般情况下不会失效，会在job中重新刷新key的过期时间和值
	 *	但是服务器资源不足，只好舍弃老数据  
	 *	(cron job周期)*2>time> (cron job周期)*1
	 */
	private static final long EXPIRE_TIME = 1*60*25;
	@Autowired
	private RedisDao redisClient;
	
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Value("${queueName}")
	private String queuename;
	
	/**
	 * 测试配置服务器更新检测
	 * @throws InterruptedException
	 */
	public void testConfig() throws InterruptedException {
		while (true) {
			Thread.sleep(2000);
			System.out.println(queuename);
		}
	}
	/**
	 * 	测试环境开，生产环境用rest
	 *  如果将queuename 注入给destination
	 *  @param content
	 */
	@JmsListener(destination = "publish.queue")
	public void control(String content) {
		log.info("========================================数据监控简单版=============================");
		log.info(content);
		
	}
	
	/**
	 * 	生产环境用rest:网页至少5s出结果
	 * 	监控12306数据拉取任务
	 */
	@GetMapping("/look")
	public BaseResponse control1() {
		// 检测任务执行状态
		LinkedList<String> datas = new LinkedList<String>();
		String data = null;
		while(true) {
			// 5000ms超时机制
			data = jms.receiveAndConvert(queue,String.class);
			if(StringUtils.isEmpty(data)) {
				break;
			}
			datas.add(data);
		}
		return BaseResponse.success(datas);
	}
	
	
	
	/**
	 * 	调度任务;实时更新12306网站的火车晚点信息;12306默认更新当前时间【-1，3】小时的数据
	 */
//	@Scheduled( cron= "0 */20 * * * ?")
//	@Scheduled( cron= "*/5 * * * * ?")
	@GetMapping("job")
	public String cron() {
		// 初始化时间
		Date date 			= new Date();
		String rq 	 = sdf0.format(date);
		String rqMin = sdf1.format(date);
		
		// 监控维度数据
		log.info("============cron job============");
		redisClient.set("http:last-job-time", rqMin);
		jms		.convertAndSend(queue, "job:"+rqMin);
        
        // 12306资源url
		// 数据在redis过期时间为30分钟
		String[] urls= {
			// K158
			// 北京西 5:12
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B1%B1%BE%A9%CE%F7&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E5-8C-97-E4-BA-AC-E8-A5-BF&tp=1541839973256",
			// 保定 4:05	
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B1%A3%B6%A8&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E4-BF-9D-E5-AE-9A&tp=1541839973256",
			// 石家庄 2:39	
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%CA%AF%BC%D2%D7%AF&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E7-9F-B3-E5-AE-B6-E5-BA-84&tp=1541839973256",
			// 邢台 1:26
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D0%CF%CC%A8&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E9-82-A2-E5-8F-B0&tp=1541839973256",
			// 邯郸 0:53
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%BA%AA%B5%A6&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E9-82-AF-E9-83-B8&tp=1541839973256",
			// 鹤壁	23:47
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%BA%D7%B1%DA&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E9-B9-A4-E5-A3-81&tp=1541839973256",
			// 新乡
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D0%C2%CF%E7&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E6-96-B0-E4-B9-A1&tp=1541839973256",
			// 郑州	21:50
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D6%A3%D6%DD&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E9-83-91-E5-B7-9E&tp=1541839973256",
			// 许昌	20:50
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D0%ED%B2%FD&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E8-AE-B8-E6-98-8C&tp=1541839973256",
			// 漯河	20:09
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%E4%F0%BA%D3&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E6-BC-AF-E6-B2-B3&tp=1541839973256",
			// 驻马店	19:11
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D7%A4%C2%ED%B5%EA&cc=K158&cxlx=0&rq=" + rq + "&czEn=-E9-A9-BB-E9-A9-AC-E5-BA-97&tp=1541839973256",
			// Z6
			// 北京西 9:48 
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B1%B1%BE%A9%CE%F7&cc=Z6&cxlx=0&rq=" + rq + "&czEn=-E5-8C-97-E4-BA-AC-E8-A5-BF&tp=1541839973256",
			// 石家庄 7:11
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%CA%AF%BC%D2%D7%AF&cc=Z6&cxlx=0&rq=" + rq + "&czEn=-E7-9F-B3-E5-AE-B6-E5-BA-84&tp=1541839973256",
			// 郑州 3:45
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%D6%A3%D6%DD&cc=Z6&cxlx=0&rq=" + rq + "&czEn=-E9-83-91-E5-B7-9E&tp=1541839973256",
			// 武昌 23:00
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%CE%E4%B2%FD&cc=Z6&cxlx=0&rq=" + rq + "&czEn=-E6-AD-A6-E6-98-8C&tp=1541839973256",
			// 长沙 19:34
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B3%A4%C9%B3&cc=Z6&cxlx=0&rq=" + rq + "&czEn=-E9-95-BF-E6-B2-99&tp=1541839973256",

			//T168
			// 北京西  13:05
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B1%B1%BE%A9%CE%F7&cc=T168&cxlx=0&rq=" + rq + "&czEn=-E5-8C-97-E4-BA-AC-E8-A5-BF&tp=1541839973256",
			// 涿州 12:08
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%E4%C3%D6%DD&cc=T168&cxlx=0&rq=" + rq + "&czEn=-E6-B6-BF-E5-B7-9E&tp=1541839973256",
			// 保定  11:14	
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B1%A3%B6%A8&cc=T168&cxlx=0&rq=" + rq + "&czEn=-E4-BF-9D-E5-AE-9A&tp=1541839973256",
			// 定州  10:30
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%B6%A8%D6%DD&cc=T168&cxlx=0&rq=" + rq + "&czEn=-E5-AE-9A-E5-B7-9E&tp=1541839973256",
			// 石家庄9:42 
			"https://www.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?cz=%CA%AF%BC%D2%D7%AF&cc=T168&cxlx=0&rq=" + rq + "&czEn=-E7-9F-B3-E5-AE-B6-E5-BA-84&tp=1541839973256"
		};
		
		// 主体逻辑: 爬取数据存储至中间件;并将监控维度数据发送至队列中间件
		for(String url:urls) {
			// 爬虫创建
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet 						  = new HttpGet(url);
			CloseableHttpResponse response = null;
			
			// 爬虫工作/redis工作/mq错误信息工作
			try {
				// 防止爬虫过快被12306识别
				Thread.sleep(2000); 
				
				// 爬虫处理
				response 		= httpClient.execute(httpGet);
				HttpEntity entity 	   = response.getEntity();
				String result  = EntityUtils.toString(entity);
				// 除去12306冗余的格式
				String data = result.replaceAll("\n\r", "").replaceAll("\n", "").replaceAll("\r", "");
				
				
				// 数据存储到redis，供前台语音程序获取源信息;第二个参数为城市/车站编码 
				WanDianInfoDto wdDto = null;
				if (url.contains("K158")) {
					 wdDto = new WanDianInfoDto("K158",url.substring(url.indexOf("czEn=")+5,url.indexOf("&tp")),rq,data);
				} else if (url.contains("Z6")) {
					 wdDto 	 = new WanDianInfoDto("Z6",url.substring(url.indexOf("czEn=")+5,url.indexOf("&tp")),rq,data);
				} else if (url.contains("T168")) {
					 wdDto = new WanDianInfoDto("T168",url.substring(url.indexOf("czEn=")+5,url.indexOf("&tp")),rq,data);
				} else {
					// 出现其他url 发送到队列;我会看看监控台
			        jms.convertAndSend(queue, "un_konwn_cc_url:"+rqMin+":"+url); 
				}	
				
				// 存储查询信息;并设置过期时间，redis sdk不完整请优化
				if (wdDto != null) {
					String jsonString =  mapper.writeValueAsString(wdDto);
					redisClient						.set(url, jsonString);
					redisClient					.expire(url, EXPIRE_TIME);
				}
			} catch (ClientProtocolException e) { 
				
				// 将错误信息发送到mq中，让我查看 mq cc_cz_updatetime(yyyy_mm_dd_min)：error   json解析错误  key cc_cz_time
				jms.convertAndSend(queue, "error1:"+rqMin+":"+url);
				log							   .info(e.toString());
			} catch (ParseException e) {
				
		        jms.convertAndSend(queue, "error2:"+rqMin+":"+url);
				log							   .info(e.toString());

			} catch (IOException e) {
				
		        jms.convertAndSend(queue, "error3:"+rqMin+":"+url);
				log							   .info(e.toString()); 
			} catch (InterruptedException e) {
				// 线程中断忽略
				e.printStackTrace();
			}
			finally {
				// 关闭资源
				if (null != response) {
					try {
						response.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != httpClient) {
					try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "job working...";
	}

	/**
	 * 查询定时任务中的redis数据
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@PostMapping("subway")
	public BaseResponse look(@RequestBody UrlData url) throws ClientProtocolException, IOException {
		String urlUrl = url.getUrl();
		String urlold = urlUrl.replaceAll("~~", "&");
		urlUrl = urlold.replaceAll("~", "?");
		String result = null ;
		try {
			result = (String) redisClient.get(urlUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.error("数据拉取失败....");
		}
		
		if(StringUtils.isEmpty(result)) {
			return BaseResponse.error("数据拉取失败....");
		}
	     //1、使用JSONObject
		 
		 JSONObject jsStr = JSONObject.parseObject(result); //将字符串{“id”：1}

	   
		return BaseResponse.success(jsStr);

	}
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException { 
		String url =  "{\"cc\":\"T168\",\"cz\":\"--\",\"updatetime\":\"2018\",\"desc\":\"xx\"}";
		
					System.out.println(JSONUtils.parse(url));
	}

}