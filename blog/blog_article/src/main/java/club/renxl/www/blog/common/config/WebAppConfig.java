package club.renxl.www.blog.common.config;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import club.renxl.www.blog.article.domain.Artitle;
import club.renxl.www.blog.intercepetors.AccessInterceptor;
@SpringBootConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");  
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
//		

		converters.add(messageConverter);
		
		 
		
		
		
		
		
	}
}

/**
 *   文章反序列化
 * @author renxl
 * @date 2018/08/29
 * @version 1.0.0
 *
 */

class ArtitleDeserializer extends JsonDeserializer<Artitle>  {

	@Override
	public Artitle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		 	JsonNode node = jp.getCodec().readTree(jp);
		 	Artitle art = new Artitle();
//		 	node.get("id").
	       /* Long id = (Long) ((IntNode) node.get("id")).numberValue();  
	        String itemName = node.get("itemName").asText();  
	        private Long id;

	        private String code;
	        private String title;

	        *//** ������ *//*
	        private String subheading;

	        *//** �ؼ��� *//*
	        private String keywords;

	        *//** ���⣬ժҪ *//*
	        private String theme;

	        *//** ����id *//*
	        private Long authorId;

	        *//** �������� *//*
	        private String authorName;

	        *//** ͼƬ��ַ *//*
	        private String image;

	        *//** ͼƬ������ת��ַ|����ͼƬ *//*
	        private String coverImg;

	        *//** ��������,��δ�õ� *//*
	        private Integer commentNum;

	        *//** �Ķ��� *//*
	        private Integer readNum;

	        *//** �������� *//*
	        private Integer zanNum;

	        *//** ����ʱ�� *//*
	        private Date createDate;

	        *//** ������ʱ�� *//*
	        private Date updateDate;

	        *//** ������Ѳ�쵥λ0��ʾ���� *//*
	        private String toPatrol;

	        *//** ������ά���� *//*
	        private String toMaintain;

	        *//** ��������ҵ�� *//*
	        private String toUsing;

	        *//** �Ƽ�ģ�壬��ҳ�����Ƽ�ģ��json *//*
	        private String recommendTemplate;

	        *//** �Ƽ����ݣ��ص��Ƽ�ģ����Ϊ�������Ƽ�����json *//*
	        private String recommendContent;

	        *//** ҳ������ *//*
	        private String footerContent;

	        *//** �ö���λ��1ȫ����2Ѳ�죬3ά����4��ҵ *//*
	        private String top;

	        *//** �Ƿ�ɾ�� *//*
	        private String del;

	        *//** ��ע�ֶ� *//*
	        private String field1;

	        *//** ��ע�ֶ� *//*
	        private String field2;

	        *//** ��ע�ֶ� *//*
	        private String field3;

	        *//** ��ע�ֶ� *//*
	        private String field4;

	        *//** ��ע�ֶ� *//*
	        private String field5;

	        *//** ��ע�ֶΣ��ݶ�Ϊ��ˣ���ǰ���� *//*
	        private Integer field6;

	        *//** ��ע�ֶ�:�����ֶ� *//*
	        private Integer field7;

	        *//** ��ǩ���� *//*
	        private String label;

	        *//** ������� *//*
	        private String type;

	        *//** ���id *//*
	        private Integer typeId;

	        *//** �������� *//*
	        private String bodys;*/
	   		return null;
	}
	
}