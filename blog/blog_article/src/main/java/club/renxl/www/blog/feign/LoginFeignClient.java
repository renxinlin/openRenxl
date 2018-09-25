package club.renxl.www.blog.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import club.renxl.www.blog.feign.hystrix.LoginFeignClientFallback;
import club.renxl.www.login.util.User;
import club.renxl.www.response.BaseResponse;

/**
 * 
 * 基于feign的声明式客户端
 * sso登录中心消费者;像dubbo一样调用消费者同时也解耦
 * 
 *  
 * 参考博客:https://blog.csdn.net/forezp/article/details/81040965
 * 参考博客源码:https://github.com/forezp/SpringCloudLearning/tree/master/sc-f-chapter3
 * 参考文献：https://springcloud.cc/spring-cloud-dalston.html#spring-cloud-feign
 * 
 * 
 * 坑：
 * 	参数为对象不行
 *  1 当参数比较复杂时，feign即使声明为get请求也会强行使用post请求
 *	2. 不支持@GetMapping类似注解声明请求，需使用@RequestMapping(value = "url",method = RequestMethod.GET)
 *  3. 使用@RequestParam注解时必须要在后面加上参数名 @RequestParam("desc") String desc
 *  4 get请求不支持传递对象
 *  5 只是
 * @author renxl 
 * @date 20180914
 * @version 1.0.0
 *
 */
// user位用户模块application的应用名称
// 当前模块，依赖，配置，启动注解;该名称不区分大小写
@FeignClient(name = "user",fallback = LoginFeignClientFallback.class)
public interface LoginFeignClient {
	
	/**
	 * 判断用户是否在线，由登录拦截器调用
	 * feign可能对GetMapping与PostMapping的支持不好
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "user/login/online",method= RequestMethod.POST)
	public BaseResponse isOnline(@RequestBody User user);	
	
	
	
	/**
	 * 登录，区分APP和WEB
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping("login/loginin")
	public BaseResponse login(@RequestBody User user, @RequestBody HttpServletRequest request, @RequestBody  HttpServletResponse response);
*/	
	
	
	/**
	 * 退出登录区分APP和用户名
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("login/loginout")
	public BaseResponse loginOut(@RequestBody User user,@RequestBody HttpServletRequest request, @RequestBody HttpServletResponse response);
	
*/
	
	
	 
	 
}
