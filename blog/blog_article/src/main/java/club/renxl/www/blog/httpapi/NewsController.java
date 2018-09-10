package club.renxl.www.blog.httpapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleVO;
import club.renxl.www.blog.article.service.NewsHandler;
import club.renxl.www.pageInfo.PageInfo;
import club.renxl.www.response.BaseResponse;

// ?id=
// read +》 dont wirte url
/**
 * 
 * 以此controller为例：数据交互全部是json，微服务之间也是；所以定义BaseResponse,不同于dubbo的domain交互
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
		log.debug("json log");
		return newsHandler.lookAllWithPage(condition);

	}
	
	
	/**
	 * 关于long类型出错问题，jackson在git上的议题暂时无法解决该问题
	 * 每个实体类写一个类型转换太过复杂，采用网页实体和传输层实体转化的方式解决该问题
	 * @param articleVo
	 * @return
	 */
	
	@PostMapping("page-detail")
	public BaseResponse readArticleDetails(@RequestBody ArtitleVO articleVo) {
		Artitle article = new Artitle();
		article.setId(Long.parseLong(articleVo.getId()));
		return newsHandler.lookDetails(article);

	}
	
	/**
	 *  <p>  发布业务:发布时，向评论表插入一条逻辑评论，做为所有评论的根评论;在评论查找时候，获取这条逻辑评论下的所有子孙评论</p>
	 *  </p> 其中根评论的topicId和id同发布文章的id;pid为null</p>
	 * @param article
	 * @return
	 */
	@PostMapping("publish")
	public BaseResponse publishArticle(@RequestBody Artitle article) {

		return newsHandler.publish(article);

	}
	
	

}
