package com.cmpl.web.style;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;

@Configuration
public class StyleConfiguration {

  @Bean
  public StyleService styleService(StyleRepository styleRepository, MediaService mediaService, FileService fileService) {
    return new StyleServiceImpl(styleRepository, mediaService, fileService);
  }

  @Bean
  public StyleDispatcher styleDispacther(StyleService styleService, StyleTranslator styleTranslator) {
    return new StyleDispatcherImpl(styleService, styleTranslator);
  }

  @Bean
  public StyleTranslator styleTranslator() {
    return new StyleTranslatorImpl();
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MetaElementFactory metaElementFactory, StyleService styleService, ContextHolder contextHolder) {
    return new StyleDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, styleService, contextHolder);
  }
}
