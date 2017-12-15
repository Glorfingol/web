package com.cmpl.web.page;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementService;

@Configuration
public class PageConfiguration {

  @Bean
  PageManagerDisplayFactory pageManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSourceImpl messageSource, PageService pageService, MetaElementFactory metaElementFactory,
      MetaElementService metaElementService, OpenGraphMetaElementService openGraphMetaElementService) {
    return new PageManagerDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, pageService,
        contextHolder, metaElementService, openGraphMetaElementService);
  }

  @Bean
  PageDispatcher pageDispatcher(PageValidator validator, PageTranslator translator, PageService pageService) {
    return new PageDispatcherImpl(validator, translator, pageService);
  }

  @Bean
  PageService pageService(PageRepository pageRepository, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, FileService fileService) {
    return new PageServiceImpl(pageRepository, metaElementService, openGraphMetaElementService, fileService);
  }

  @Bean
  PageValidator pageValidator(WebMessageSourceImpl messageSource) {
    return new PageValidatorImpl(messageSource);
  }

  @Bean
  PageTranslator pageTranslator() {
    return new PageTranslatorImpl();
  }
}
