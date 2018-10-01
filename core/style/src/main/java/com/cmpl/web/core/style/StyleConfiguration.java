package com.cmpl.web.core.style;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.Style;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Style.class)
@EnableJpaRepositories(basePackageClasses = StyleRepository.class)
public class StyleConfiguration {

  @Bean
  public StyleDAO styleDAO(StyleRepository styleRepository, ApplicationEventPublisher publisher) {
    return new DefaultStyleDAO(styleRepository, publisher);
  }

  @Bean
  public StyleMapper styleMapper(MediaService mediaService, FileService fileService) {
    return new StyleMapper(mediaService, fileService);
  }

  @Bean
  public StyleService styleService(StyleDAO styleDAO, StyleMapper styleMapper,
    MediaService mediaService,
    FileService fileService) {
    return new DefaultStyleService(styleDAO, styleMapper, mediaService, fileService);
  }

  @Bean
  public StyleDispatcher styleDispacther(StyleService styleService,
    StyleTranslator styleTranslator) {
    return new DefaultStyleDispatcher(styleService, styleTranslator);
  }

  @Bean
  public StyleTranslator styleTranslator() {
    return new DefaultStyleTranslator();
  }

  @Bean
  public RenderingStyleMapper renderingStyleMapper(MediaService mediaService) {
    return new RenderingStyleMapper(mediaService);
  }

  @Bean
  public RenderingStyleService renderingStyleService(StyleDAO styleDAO,
    RenderingStyleMapper renderingStyleMapper) {
    return new DefaultRenderingStyleService(styleDAO, renderingStyleMapper);
  }

}
