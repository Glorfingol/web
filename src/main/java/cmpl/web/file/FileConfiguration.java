package cmpl.web.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;

@Configuration
public class FileConfiguration {

  @Bean
  ImageConverterService imageConverterService() {
    return new ImageConverterServiceImpl();
  }

  @Bean
  ImageService imageService(ContextHolder contextHolder, ImageConverterService imageConverterService) {
    return new ImageServiceImpl(contextHolder, imageConverterService);
  }

  @Bean
  FileService fileService(ContextHolder contextHolder) {
    return new FileServiceImpl(contextHolder);
  }

}
