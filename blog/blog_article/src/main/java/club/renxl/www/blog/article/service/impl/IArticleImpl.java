package club.renxl.www.blog.article.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import club.renxl.www.blog.article.dao.ArtitleMapper;
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.blog.article.service.IArticle;

public class IArticleImpl implements IArticle {

	@Autowired
	private ArtitleMapper artitleMapper; 
	@Override
	public Map<String, Object> selectByPageInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

		ArtitleExample example = new ArtitleExample();
		List<Artitle> result = artitleMapper.selectByExample(example );
        PageInfo<Artitle> pageInfo = new PageInfo<Artitle>(result);

		return null;
	}

}
