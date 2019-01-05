package club.renxl.www.blog.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import zipkin.server.EnableZipkinServer;

/**
 * 服务调用链监控
 * 
 * @author renxl
 * @date 2018/10/27
 * @version 1.0.0
 *
 */
@SpringBootApplication
public class OauthServerApplication {

   public static void main(String[] args) {
      SpringApplication.run(OauthServerApplication.class, args);
   }
}