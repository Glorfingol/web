package com.cmpl.web.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;

@Configuration
public class LoginConfiguration {

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource);
  }

}
