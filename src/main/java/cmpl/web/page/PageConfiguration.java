package cmpl.web.page;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.file.FileService;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementService;
import cmpl.web.meta.OpenGraphMetaElementService;

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
