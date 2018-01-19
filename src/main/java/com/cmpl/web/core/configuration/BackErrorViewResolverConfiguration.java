package com.cmpl.web.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.error.BackErrorViewResolver;

@Configuration
public class BackErrorViewResolverConfiguration {

  @Bean
  public BackErrorViewResolver backErrorViewResolver() {
    return new BackErrorViewResolver();
  }

}
