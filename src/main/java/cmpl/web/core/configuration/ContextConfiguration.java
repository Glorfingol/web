package cmpl.web.core.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;

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

  @Value("${templateBasePath}")
  String templateBasePath;

  @Value("${mediaBasePath}")
  String mediaBasePath;

  @Value("${mediaDisplayPath}")
  String mediaDisplayPath;

  @Bean
  ContextHolder contextHolder() {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");

    ContextHolder contextHolder = new ContextHolder();

    contextHolder.setDateFormat(dateFormat);
    contextHolder.setImageDisplaySrc(imageDisplaySrc);
    contextHolder.setImageFileSrc(fileBasePath);
    contextHolder.setElementsPerPage(10);
    contextHolder.setTemplateBasePath(templateBasePath);
    contextHolder.setMediaBasePath(mediaBasePath);
    contextHolder.setMediaDisplayPath(mediaDisplayPath);
    return contextHolder;

  }

}
