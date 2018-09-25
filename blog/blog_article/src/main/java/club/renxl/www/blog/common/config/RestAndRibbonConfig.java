package club.renxl.www.blog.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



/**
 * 配置负载均衡
 * @author renxl
 * @date 20180920
 *
 */
@Configuration
public class RestAndRibbonConfig {
	/**
	 * rest方式进行服务调用
	 * @return
	 */
	@Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
