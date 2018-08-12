package club.renxl.www.blog.httpapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.blog.article.service.IWelcome;
import club.renxl.www.response.BaseResponse;

@RestController
@RequestMapping("welcome")
public class WelcomeController {
	
	
	
	@Autowired
	private IWelcome iWelcome;
	@RequestMapping("hi")
	public BaseResponse wel() {
		return BaseResponse.success();
		
	}
	@RequestMapping("hi1")
	public String hi() {
		String wel = iWelcome.wel();
		return wel;
	}

}

