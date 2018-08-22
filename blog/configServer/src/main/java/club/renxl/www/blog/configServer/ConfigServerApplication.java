package club.renxl.www.blog.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 * 
 * @author renxl
 * @date 20180815
 *
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
	
    public static void main(String[] args) {
    	
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}