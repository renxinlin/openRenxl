package com.blog.article.http.api;

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

