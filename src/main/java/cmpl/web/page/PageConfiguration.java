package cmpl.web.page;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.file.FileService;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementService;
import cmpl.web.meta.OpenGraphMetaElementService;

@Configuration
public class PageConfiguration {

  @Bean
  PagesManagerDisplayFactory pagesManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, PageService pageService,
      MetaElementFactory metaElementFactory) {
    return new PagesManagerDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory,
        pageService, contextHolder);
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
