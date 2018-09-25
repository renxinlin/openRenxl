package club.renxl.www.blog.feign.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;

import club.renxl.www.blog.feign.LoginFeignClient;
import club.renxl.www.enums.SystemLogEntity;
import club.renxl.www.enums.UserResponse;
import club.renxl.www.login.util.User;
import club.renxl.www.response.BaseResponse;
/**
 * <p> 基于声明式的hystrix  </p>
 * <p>基于rest的hystrix    @HystrixCommand(fallbackMethod = "hiFallback") </p>
 * 
 * 
 * @author renxl
 * @date 2018/09/21
 * @version 1.0.0
 *
 */
@Service
public class LoginFeignClientFallback implements LoginFeignClient {
	private static final Logger log = LoggerFactory.getLogger(LoginFeignClientFallback.class);
	/**
	 * 断路器响应
	 */
	@Override
	public BaseResponse isOnline(User user) {
		// 日志记录
		log.warn(JSONUtils.toJSONString(new SystemLogEntity(this.getClass().getName(),"isOnline(User user)",
				UserResponse.LOGIN_MCRIO_SERVICE_ERROR.getCode(), UserResponse.LOGIN_MCRIO_SERVICE_ERROR.getMsg()
				)));
		
		// 调用方
		return BaseResponse.error(UserResponse.LOGIN_MCRIO_SERVICE_ERROR.getCode(), UserResponse.LOGIN_MCRIO_SERVICE_ERROR.getMsg());
	}

}
