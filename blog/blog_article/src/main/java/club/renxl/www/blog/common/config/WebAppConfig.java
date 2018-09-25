package club.renxl.www.blog.common.config;

import java.math.BigInteger;
import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import club.renxl.www.blog.intercepetors.AccessInterceptor;
import club.renxl.www.blog.intercepetors.LoginInterceptor;


/**
 * 拦截器;响应格式等mvc层配置
 * @author renxl
 * @date 2018/09/14
 * @version 1.0.0
 */
@SpringBootConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
	/**
	 * 提前加载bean，解决mvc无法注入的问题
	 * @return
	 */
	@Bean
    public LoginInterceptor getLoginInterceptorr(){
        return new LoginInterceptor();
    }
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");  
        registry.addInterceptor(getLoginInterceptorr()).addPathPatterns("/**/login");
    }  
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		
		/**
		 *  将Long,BigInteger序列化的时候,转化为String
		 */
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
		simpleModule.addSerializer(String.class,ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		
		messageConverter.setObjectMapper(objectMapper);
		
//		
//		simpleModule.addDeserializer(Artitle.class, new ArtitleDeserializer () );  
//		objectMapper.registerModule(simpleModule); 

		converters.add(messageConverter);
		
	}
}