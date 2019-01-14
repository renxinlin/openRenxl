package club.renxl.www.blog.article.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 晚点信息查询实体
 * @author renxl
 * @date 2018/12/22
 *
 */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) 
public class WanDianInfoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 12306命名格式:车次
	 */
	private String cc;
	
	
	/**
	 * 12306命名格式:车站
	 */
	
	private String cz;
	
	
	
	/**
	 * updatetime(yyyy_mm_dd_min)
	 */
	private String updatetime;
	
	/**
	 * 晚点信息;用于基于oauth2实现的语音播报;待转语音的晚点描述文字信息
	 */
	private String desc;

}
