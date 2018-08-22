package club.renxl.www.blog.httpapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.service.NewsHandler;
import club.renxl.www.pageInfo.PageInfo;
import club.renxl.www.response.BaseResponse;

// ?id=
// read +》 dont wirte url
/**
 * 公告 noticeVO的type对应的是noticeDo的top
 * 
 * 
 * @author renxl
 * @date 2018/04/26
 */
@RestController
@RequestMapping("news")
public class NewsController {
	private static Logger log = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	private NewsHandler newsHandler;

	/**
	 * 关于日志设计思想：采用拦截器，拦截器中采用线程池+异步的方式发送至队列，队列消费者存储日志到中间件，定期增量同步至数据库 日志统一采用log对象存储
	 * 
	 * @param condition
	 * @return
	 */
	@PostMapping("pages")

	public BaseResponse readArticle(@RequestBody PageInfo<Artitle> condition) {

		return newsHandler.lookAllWithPage(condition);

	}

}
