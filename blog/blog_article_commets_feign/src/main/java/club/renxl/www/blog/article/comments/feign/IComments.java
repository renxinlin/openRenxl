package club.renxl.www.blog.article.comments.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


/**
 * env test
 * @author renxl
 *
 */
@RestController
@RequestMapping("cus")
public class IComments {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("get")
    @HystrixCommand(fallbackMethod = "getByRibbonFall")
	public Object getByRibbon() {
		System.out.println(" =================================================120" );
		int x =1/0;
		System.out.println("--------------------------------------------------121");
		return restTemplate.getForObject("http://article/welcome/hi1", Object.class);
	}
	public Object getByRibbonFall() {
		System.out.println("======================================================");
		return "业务异常...";
	}
}
