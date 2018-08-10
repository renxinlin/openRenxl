package club.renxl.www.blog.httpapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.renxl.www.response.BaseResponse;

@RestController
@RequestMapping("welcome")
public class WelcomeController {
	
	@RequestMapping("hi")
	public BaseResponse wel() {
		return BaseResponse.success();
		
	}

}

