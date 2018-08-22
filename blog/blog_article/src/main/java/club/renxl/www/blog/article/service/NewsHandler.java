package club.renxl.www.blog.article.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

 
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.pageInfo.PageInfo;
import club.renxl.www.response.BaseResponse;

/**
 * 接口同时被rpc和http依赖
 * 新闻
 * 
 * @author renxl
 * @date 2018/04/25
 * 
 */
@Service
public interface NewsHandler {
	/**
	 * 业务操作
	 * @param record
	 * @return
	 */
	boolean createNews(Artitle record);

 	Map<String, Object> lookAll(Artitle Artitle,
 			PageInfo<Artitle> p);

	Artitle lookDetails(Artitle Artitle);
	
	boolean toggleTopOrUpdateNews(Artitle record);
	boolean deleteById(Artitle Artitle) ;
	
	/**
	 * app banner位展示
	 * @param noticeVO
	 * @return
	 */
	 
	long countByExample(ArtitleExample example);

	int deleteByExample(ArtitleExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Artitle record);

	int insertSelective(Artitle record);

	List<Artitle> selectByExample(ArtitleExample example);

	Artitle selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Artitle record,
			@Param("example") ArtitleExample example);

	int updateByExample(@Param("record") Artitle record,
			@Param("example") ArtitleExample example);

	int updateByPrimaryKeySelective(Artitle record);

	int updateByPrimaryKey(Artitle record);
	
	/**
	 * 分页查询
	 * @param condition
	 * @return
	 */
	BaseResponse lookAllWithPage(PageInfo<Artitle> condition);
	

}