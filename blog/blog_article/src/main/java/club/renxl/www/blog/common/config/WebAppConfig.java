package club.renxl.www.blog.common.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import club.renxl.www.blog.intercepetors.AccessInterceptor;
@SpringBootConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");  
    }  
}