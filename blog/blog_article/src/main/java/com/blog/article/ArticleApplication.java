package com.blog.article;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *   文章管理发布 == > 演进方向，加视频 评论 图片 文章类别（社交方向）
 *   @port 8990
 * @author renxl
 *
 */
@SpringBootApplication
@EnableEurekaClient // 每一个微服务都是一个eureka客户端
public class ArticleApplication {
	
	/**
	 * 文章基础提供者
	 * @param args
	 */
	// 基础提供者一律采用restTemplate
	// 消费者一律采用声明式rest feign
	public static void main(String[] args) {
		new SpringApplicationBuilder(ArticleApplication.class).web(true).run(args);

	}

}
