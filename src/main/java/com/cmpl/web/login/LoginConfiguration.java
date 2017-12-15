package com.cmpl.web.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.meta.MetaElementFactory;

@Configuration
public class LoginConfiguration {

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory);
  }

}
