package com.cmpl.web.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

@Configuration
public class LoginConfiguration {

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, breadCrumbs);
  }

}
