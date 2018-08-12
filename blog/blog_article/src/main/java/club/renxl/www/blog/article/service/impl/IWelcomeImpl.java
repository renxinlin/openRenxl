package club.renxl.www.blog.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.renxl.www.blog.article.dao.ArtitleMapper;
import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.domain.ArtitleExample;
import club.renxl.www.blog.article.service.IWelcome;

@Service
public class IWelcomeImpl implements IWelcome {
	@Autowired
	private ArtitleMapper artitleMapper;
	
	public String	wel() {
		ArtitleExample example = new 		ArtitleExample ();
		List<Artitle> selectByExample = artitleMapper.selectByExample(example );
		return selectByExample.toString();
		
	}
}
