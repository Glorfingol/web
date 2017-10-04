package cmpl.web.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.footer.FooterFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.page.PageService;

@Configuration
public class MenuConfiguration {

  @Bean
  MenuService menuService(MenuRepository menuRepository) {
    return new MenuServiceImpl(menuRepository);
  }

  @Bean
  MenuFactory menuFactory(WebMessageSourceImpl messageSource, MenuService menuService) {
    return new MenuFactoryImpl(messageSource, menuService);
  }

  @Bean
  MenuValidator menuValidator() {
    return new MenuValidatorImpl();
  }

  @Bean
  MenuTranslator menuTranslator() {
    return new MenuTranslatorImpl();
  }

  @Bean
  MenuDispatcher menuDispatcher() {
    return new MenuDispatcherImpl();
  }

  @Bean
  MenuManagerDisplayFactory menuManagerDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory, MenuService menuService,
      PageService pageService, ContextHolder contextHolder) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, footerFactory, messageSource, metaElementFactory,
        menuService, pageService, contextHolder);
  }
}
