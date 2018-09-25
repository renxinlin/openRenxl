package club.renxl.www.blog.intercepetors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 请求头处理，传递请求头到feign
 * 
 * @author renxl
 * @date 2018/09/21
 * @version 1.0.0
 * 
 *
 */
public class FeignClientRequestHeaderInterceptor {

	@Bean
	public RequestInterceptor getRequestInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				// 获取请求头信息
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest request		 = attributes.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				
				// 设置请求头
				if (headerNames != null) {
					/*
					 *	 链式传递请求头信息;避免服务之间调用时请求头丢失
					 *	包含token;cookies;等请求头信息     
					 */
					while (headerNames.hasMoreElements()) {
						String name 	= headerNames.nextElement();
						String values 	  = request.getHeader(name);
						template			  .header(name, values);
					}
				}
			}

		};

	}

}
