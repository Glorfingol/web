package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.error.BackErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackErrorViewResolverConfiguration {

  @Bean
  public BackErrorViewResolver backErrorViewResolver() {
    return new BackErrorViewResolver();
  }

}
