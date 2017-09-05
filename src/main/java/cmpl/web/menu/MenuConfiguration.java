package cmpl.web.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSourceImpl;

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

}
