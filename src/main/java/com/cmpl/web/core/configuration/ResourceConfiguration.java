package com.cmpl.web.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.message.WebMessageSourceImpl;

/**
 * Configuration des cles i18n
 * 
 * @author Louis
 *
 */
@Configuration
public class ResourceConfiguration {

  /**
   * Declaration des sources de cles i18n
   * 
   * @return
   */
  @Bean
  public WebMessageSource messageSource() {
    WebMessageSourceImpl source = new WebMessageSourceImpl();
    source.setBasenames("i18n/footer", "i18n/keys", "i18n/back", "i18n/error", "i18n/form");
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
  }
}
