package cmpl.web.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;

@Configuration
public class LoginConfiguration {

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory);
  }

}
