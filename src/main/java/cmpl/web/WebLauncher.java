package cmpl.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebLauncher {

  public static void main(String[] args) {
    SpringApplication.run(WebLauncher.class, args);
  }

}
