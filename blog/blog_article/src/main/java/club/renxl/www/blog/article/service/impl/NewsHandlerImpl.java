package club.renxl.www.blog.article.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SimpleIdGenerator;
import org.springframework.util.StringUtils;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.IPIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import club.renxl.www.blog.article.dao.ArtitleMapper;
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.blog.article.domain.Comment;
import club.renxl.www.blog.article.service.ICommentReply;
import club.renxl.www.blog.article.service.NewsHandler;
import club.renxl.www.enums.UserResponse;
import club.renxl.www.exceptions.BizException;
import club.renxl.www.pageInfo.PageUtil;
import club.renxl.www.response.BaseResponse;

/**
 * 接口同时被rpc和http依赖 新闻
 * 
 * @author renxl
 * @date 2018/04/25
 * 
 */
@Service("newsHandlerImpl")
public class NewsHandlerImpl implements NewsHandler {
	private static Logger log = LoggerFactory.getLogger(NewsHandlerImpl.class);
 
	@Autowired
    private IdGenerator idGenerator;
	
	@Autowired
	private ArtitleMapper artitleMapper;
	
	
	@SuppressWarnings("unused")
	@Autowired
	private ICommentReply iCommentReply;


	@Override
	public boolean createNews(Artitle record) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Map<String, Object> lookAll(Artitle Artitle, club.renxl.www.pageInfo.PageInfo<Artitle> p) {
		// TODO Auto-generated method stub
		return null;
	}
	 

 

	@Override
	public boolean toggleTopOrUpdateNews(Artitle record) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean deleteById(Artitle Artitle) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public long countByExample(ArtitleExample example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int deleteByExample(ArtitleExample example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int insert(Artitle record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int insertSelective(Artitle record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Artitle> selectByExample(ArtitleExample example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Artitle selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int updateByExampleSelective(Artitle record, ArtitleExample example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updateByExample(Artitle record, ArtitleExample example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updateByPrimaryKeySelective(Artitle record) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updateByPrimaryKey(Artitle record) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 分页查询
	 */
	@Override
	public BaseResponse lookAllWithPage(club.renxl.www.pageInfo.PageInfo<Artitle> condition) {
		PageHelper.startPage(condition.getPage(), condition.getRows());
		ArtitleExample example = new ArtitleExample();
		example.setOrderByClause("update_date desc");
		List<Artitle> results = artitleMapper.selectByExampleWithBLOBs(example);
        PageInfo<Artitle> pageInfo = new PageInfo<Artitle>(results);
        PageUtil<Artitle> pt= new PageUtil<Artitle>();
        club.renxl.www.pageInfo.PageInfo<Artitle> pageInfoByPageInfo = pt.getPageInfoByPageInfo(pageInfo, condition.getCondtion());
		return BaseResponse.success(pageInfoByPageInfo);
	}


	@Override
	public BaseResponse lookDetails(Artitle article) {
		log.debug(article.toString());
		if(article == null || article.getId() == null) {
			return BaseResponse.error(UserResponse.ArgsError.getCode(),  "发生错误");
			// log to  json
			// log.warn('');
		}
		Artitle articleData = artitleMapper.selectByPrimaryKey(article.getId());
		Comment comment = new Comment(article.getId());
		BaseResponse commentsBaseResponse = iCommentReply.lookCommentReply(comment ); 
		// 树结构
		if(commentsBaseResponse.getCode() == BaseResponse.success().getCode()){
			Object data = commentsBaseResponse.getData();
			articleData.setComments(data);
		}
		
		
		return BaseResponse.success(articleData);
	}


	@Override
	public BaseResponse publish(Artitle article)  {
		
		boolean legal = validateArticle(article);
		if(!legal) {
			return BaseResponse.argsError("参数校验失败");
		}
		article = addOtherFieldWhenPublish(article);
		int insert = artitleMapper.insert(article);
		// Comment comment = initLogicRootCommentByArticle(article);
		// iCommentReply.publishComment(comment);
		return BaseResponse.success("成功发布" + insert + "条!");// 喜欢java这个用法
	}

	 

	/**
	 *  	发布文章的时候发布逻辑根节点
	 * @param article
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("unused")
	private Comment initLogicRootCommentByArticle(Artitle article) throws BizException {
		if (article.getId() == null) {
			throw new BizException(UserResponse.ArgsError.getMsg());
		}
		Comment comment					    = new Comment();
		comment.setId(idGenerator.generateId().longValue());
		comment				   .setTopicId(article.getId());
		comment					 .setCreateTime(new Date());
		 
		return comment;
	}


	private Artitle addOtherFieldWhenPublish(Artitle article) {
		article.setCreateDate(new Date());
		article.setUpdateDate(new Date());
		article.setImage(article.getCoverImg());
		int i = 0;
		// TODO 不知道有没有并发问题，线下单机获取50000/s
		// start 解决id隔一段时间生成老是偶数问题
		int num = (int) (Math.random()*50);
		while(i < num) {
			i++;
			idGenerator.generateId().longValue();
		}
		// end 解决id隔一段时间生成老是偶数问题

		article.setId(idGenerator.generateId().longValue());
		//TODO 待添加
		while(i <3) {
			i++;
			System.out.println(idGenerator.generateId().longValue());
		}
		return article;
	}
	public static void main(String[] args) {
		int i = 0;
		while(i<100) {
		//	System.out.println(new CommonSelfIdGenerator().generateId().longValue());
			long l = new CommonSelfIdGenerator().generateId().longValue();
			System.out.println(l-241331097688866816l);
			System.out.println(l);
			System.out.println(l-l);
			System.out.println((long)(Math.random()*241331097688866816L));
			System.out.println(l-(long)(Math.random()*241331097688866816L));

			i++;
		}
	}


	/**
	 * 发布参数校验
	 * @param article
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean validateArticle(Artitle article) {
		if(StringUtils.isEmpty(article)) {
			return false;

		}
		
		
		if(StringUtils.isEmpty(article.getTitle())) {
			return false;

		}
	 
		if(StringUtils.isEmpty(article.getBodys())) {
			return false;

		}
	 
		
		// 封面图片
		if(StringUtils.isEmpty(article.getCoverImg())) {
			return false;

		}
		
		// 分页摘要
		if(StringUtils.isEmpty(article.getTheme())) {
			return false;

		}
		// 关键词
		if(StringUtils.isEmpty(article.getKeywords())) {
			return false;

		}
		
		// http://tools.jb51.net/regex/create_reg 正则在线生成
		// 正则回溯测试:有网站和工具，网站暂时没找到
		// 校验正则 略
		return true;
		 
	}
 
	
 
 
}