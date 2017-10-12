package cmpl.web.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.core.context.ContextHolder;
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
  MenuValidator menuValidator(WebMessageSourceImpl messageSource) {
    return new MenuValidatorImpl(messageSource);
  }

  @Bean
  MenuTranslator menuTranslator() {
    return new MenuTranslatorImpl();
  }

  @Bean
  MenuDispatcher menuDispatcher(MenuValidator validator, MenuTranslator translator, MenuService menuService,
      PageService pageService) {
    return new MenuDispatcherImpl(validator, translator, menuService, pageService);
  }

  @Bean
  MenuManagerDisplayFactory menuManagerDisplayFactory(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory, MenuService menuService, PageService pageService,
      ContextHolder contextHolder) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, menuService, pageService,
        contextHolder);
  }
}
