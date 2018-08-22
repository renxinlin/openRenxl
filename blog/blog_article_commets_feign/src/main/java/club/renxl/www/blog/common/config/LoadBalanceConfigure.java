package club.renxl.www.blog.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ribbon 负载均衡
 * @author win10
 *
 */
@Configuration
public class LoadBalanceConfigure {
	
	/**
	 * ribbon 负载均衡
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
