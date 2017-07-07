package cmpl.web.configuration;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.model.context.ContextHolder;

/**
 * COnfiguration du contextHolder a partir de donnes du fichier yaml
 * 
 * @author Louis
 *
 */
@Configuration
public class ContextConfiguration {

  @Value("${fileBasePath}")
  String fileBasePath;

  @Value("${imageDisplaySrc}")
  String imageDisplaySrc;

  @Bean
  ContextHolder contextHolder() {

    String dayMonthYearEuropeanPattern = "dd/MM/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(dayMonthYearEuropeanPattern);

    ContextHolder contextHolder = new ContextHolder();

    contextHolder.setDateFormat(dateFormat);
    contextHolder.setImageDisplaySrc(imageDisplaySrc);
    contextHolder.setImageFileSrc(fileBasePath);
    return contextHolder;

  }

}
