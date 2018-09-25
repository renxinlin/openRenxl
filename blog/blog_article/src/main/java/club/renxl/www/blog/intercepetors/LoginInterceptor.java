package club.renxl.www.blog.intercepetors;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import club.renxl.www.blog.feign.LoginFeignClient;
import club.renxl.www.enums.ResponseCode;
import club.renxl.www.login.util.LoginConstant;
import club.renxl.www.login.util.LoginUtil;
import club.renxl.www.login.util.User;
import club.renxl.www.response.BaseResponse;

/**
 * 登录拦截器,通过fegin调用sso登录中心
 * 在自身的模块中配置拦截的路径
 * @author renxl
 * @date 2018/09/14
 * @version 1.0.0
 *
 */
@Component
public class LoginInterceptor  extends HandlerInterceptorAdapter {
	/**
	 * 开关配置，通过配置服务器动态切换回调页面
	 * 
	 * 1 用户url
	 * 2 系统置顶url
	 */
	@Value("${sso.login.web.urlcontrol.zuul}")
	private String openConfig ;
	/**
	 * 
	 * 单点登录网页 url
	 */
	@Value("${sso.login.web.url}")
	private String ssoLoginUrl;
	
	@Autowired
	private  LoginFeignClient loginFeign;
	
	@Autowired
	private RestTemplate restTemplate ;
	/**
	 * 判断用户是否在线
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("openConfig"+openConfig);
		
			// http服务调用;获取loginway;logintype;token等参数
			boolean login 	  										=  false;
			User user		 = LoginUtil.fromLoginParamToUserEntity(request);
			 
			BaseResponse isOnlineResult = null;
			try {
				isOnlineResult = loginFeign.isOnline(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 登录成功
			login = isOnlineBySsoResult(isOnlineResult);
			if(login) {
				return true;
			}
		
		// 登录失败;跳转登录页面
		//TODO 将当前url作为参数传递给登录页面url|或者指定url;用于登录成功后的跳转
		String requestURL = request.getRequestURL().toString();
		if(!openConfig.equals(LoginConstant.REDIRECT_USER_URL)) {
			requestURL = ssoLoginUrl;
		}
		response.sendRedirect(requestURL);
		return false;
	}
	
	
	/**
	 * 根据单点登录中心的登录结果判断用户是否登录
	 * @param login
	 * @param isOnlineResult
	 * @return
	 */
	private boolean isOnlineBySsoResult( BaseResponse isOnlineResult) {
		if(isOnlineResult == null ) {
			return false;
		}
		
		// int值比较
		if(ResponseCode.SUCCESS.getCode() ==  isOnlineResult.getCode()) {
		return true;	
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	

}
