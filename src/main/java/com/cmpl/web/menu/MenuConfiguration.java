package com.cmpl.web.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.PageService;

@Configuration
public class MenuConfiguration {

  @Bean
  MenuService menuService(MenuRepository menuRepository) {
    return new MenuServiceImpl(menuRepository);
  }

  @Bean
  MenuFactory menuFactory(WebMessageSource messageSource, MenuService menuService) {
    return new MenuFactoryImpl(messageSource, menuService);
  }

  @Bean
  MenuValidator menuValidator(WebMessageSource messageSource) {
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
  MenuManagerDisplayFactory menuManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MenuService menuService, PageService pageService, ContextHolder contextHolder) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, messageSource, menuService, pageService, contextHolder);
  }
}
