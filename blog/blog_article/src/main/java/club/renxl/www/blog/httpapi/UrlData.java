package club.renxl.www.blog.httpapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) 
public class UrlData {
	private String url;

}
