package cmpl.web.style;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.file.FileService;
import cmpl.web.media.MediaService;

@Configuration
public class StyleConfiguration {

  @Bean
  public StyleService styleService(StyleRepository styleRepository, MediaService mediaService, FileService fileService) {
    return new StyleServiceImpl(styleRepository, mediaService, fileService);
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(StyleService styleService, ContextHolder contextHolder) {
    return new StyleDisplayFactoryImpl(styleService, contextHolder);
  }
}
