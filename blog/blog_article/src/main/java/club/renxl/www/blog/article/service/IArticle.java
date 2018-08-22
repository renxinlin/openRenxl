package club.renxl.www.blog.article.service;

import java.util.Map;

public interface IArticle {
	/**
	 * 分页查询
	 * @return
	 */
	Map<String, Object> selectByPageInfo(Integer pageNum, Integer pageSize);
}
