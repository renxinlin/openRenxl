package club.renxl.www.blog.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

/**
 * 服务调用链监控
 * 
 * @author renxl
 * @date 2018/10/27
 * @version 1.0.0
 *
 */
@EnableZipkinServer
@SpringBootApplication
public class ZipkinServerApplication {

   public static void main(String[] args) {
      SpringApplication.run(ZipkinServerApplication.class, args);
   }
}