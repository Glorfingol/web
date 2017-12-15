package com.cmpl.web.core.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuration de la locale
 * 
 * @author Louis
 *
 */
@Configuration
public class LocaleConfiguration {

  /**
   * Declaration de la locale du projet et de la locale par defaut
   * 
   * @return
   */
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.FRANCE);
    return slr;
  }

}
