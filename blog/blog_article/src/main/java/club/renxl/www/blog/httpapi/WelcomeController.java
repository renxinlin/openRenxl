package club.renxl.www.blog.httpapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.ribbon.proxy.annotation.Hystrix;

import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.article.service.IWelcome;
import club.renxl.www.blog.feign.LoginFeignClient;
import club.renxl.www.login.util.User;
import club.renxl.www.response.BaseResponse;

@RestController
@RequestMapping("welcome")
public class WelcomeController {
	// 配置服务器搭建完成，测试ok
	private String m;
	@Autowired
	private IWelcome iWelcome;
	
	
	@Value("${sso.login.web.url}")
	private String ssoLoginUrl;
	
	@Autowired
	private  LoginFeignClient loginFeign;
	
	@RequestMapping("hi")
	public BaseResponse wel() {
		User user = null;
		loginFeign.isOnline(user );
		return BaseResponse.success(m);
		
	}

	@RequestMapping("hi1")
	public  List<Artitle> hi() {
		  List<Artitle> wel = iWelcome.wel();
		return wel;
	}

}

