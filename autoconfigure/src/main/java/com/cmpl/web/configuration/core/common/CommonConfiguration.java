package com.cmpl.web.configuration.core.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cmpl.web.manager.ui.core.administration.user.CurrentUserAuditorAware;

@Configuration
@Import({BackErrorViewResolverConfiguration.class, ContextConfiguration.class, EventsListenerConfiguration.class,
    LocaleConfiguration.class, MailConfiguration.class, ResourceConfiguration.class,
    TemplateResolverConfiguration.class, WebSecurityConfiguration.class})
public class CommonConfiguration {

  @Bean
  public CurrentUserAuditorAware auditorProvider() {
    return new CurrentUserAuditorAware();
  }

}
