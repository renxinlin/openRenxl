package club.renxl.www.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * 文章管理发布 == > 演进方向，加视频 评论 图片 文章类别（社交方向）
 * @port 8990
 * @author renxl
 *
 */
@SpringBootApplication
@EnableEurekaClient // 每一个微服务都是一个eureka客户端,该注解表明会拉取服务端信息，无该注解只注册不拉取信息
@EnableFeignClients
@MapperScan("club.renxl.www.blog.article.dao")
@EnableScheduling
@EnableCircuitBreaker// or @EnableHystrix
@EnableHystrixDashboard
@EnableZuulProxy 
public class ArticleApplication {
	
	
	/**
	 * 文章基础提供者
	 * @param args
	 */
	// 基础提供者一律采用restTemplate
	// 消费者一律采用声明式rest feign
    @HystrixCommand
	public static void main(String[] args) {
		new SpringApplicationBuilder(ArticleApplication.class).web(true).run(args);
		System.out.println("======老板出没,红包发来=======");

	}

}
