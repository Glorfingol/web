package com.cmpl.web.login;

import org.springframework.beans.factory.annotation.Qualifier;
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
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, breadCrumbRegistry);
  }

}
