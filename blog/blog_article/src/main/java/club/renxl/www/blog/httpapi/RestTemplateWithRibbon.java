package club.renxl.www.blog.httpapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class RestTemplateWithRibbon {
	
	@Value("${server.port}")
	private String name;
	
	@Autowired
	private LoadBalancerClient loadBalancer; 

	
	
	@Autowired
	private RestTemplate testTmp; 

	
	@GetMapping("ribbon-hi")
	public String hi() {
		return name;
	}
	
	
	@GetMapping ("e-info")
	public String testRibbon () {
	ServiceInstance instance = loadBalancer.choose ("eurekaServer");
	return instance.getHost ()+":"+instance.getPort(); 
	}
	
	
//	@GetMapping ("e-info1")
//	public String testRibbon1 () {
//		return "1";
//	}
	
	@GetMapping ("e-info1")
	@HystrixCommand(fallbackMethod ="testHytrix") 
	public String testRibbon1 () {
		System.out.println("===============================im in request==========================");
		return testTmp.getForObject("http://articleaab/article/e-info1", String.class);
	}
	
	
	public String testHytrix () {
		System.out.println("===============================im in Hytrix==========================");

		return "hystrix say : error";
	}

}
