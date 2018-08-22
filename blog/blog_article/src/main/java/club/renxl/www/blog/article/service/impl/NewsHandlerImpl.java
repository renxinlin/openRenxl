package club.renxl.www.blog.article.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.nntp.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 

import club.renxl.www.blog.article.dao.ArtitleMapper;
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.blog.article.service.NewsHandler;
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
	private ArtitleMapper artitleMapper;


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
	public Artitle lookDetails(Artitle Artitle) {
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
		List<Artitle> results = artitleMapper.selectByExample(example );
        PageInfo<Artitle> pageInfo = new PageInfo<Artitle>(results);
        PageUtil<Artitle> pt= new PageUtil<Artitle>();
        club.renxl.www.pageInfo.PageInfo<Artitle> pageInfoByPageInfo = pt.getPageInfoByPageInfo(pageInfo, condition.getCondtion());
		return BaseResponse.success(pageInfoByPageInfo);
	}
 
	
 
 
}