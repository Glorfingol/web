package com.cmpl.web.facebook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.news.NewsEntryService;

@Configuration
@EnableSocial
public class FacebookConfiguration {

  @Bean
  FacebookImportTranslator facebookImportTranslator() {
    return new FacebookImportTranslatorImpl();
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookService facebookService) {
    return new FacebookDisplayFactoryImpl(menuFactory, messageSource, facebookService);
  }

  @Bean
  FacebookDispatcher facebookDispatcher(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return new FacebookDispatcherImpl(facebookImportService, facebookImportTranslator);
  }

  @Bean
  FacebookService facebookService(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    return new FacebookServiceImpl(contextHolder, facebookConnector, connectionRepository, newsEntryService);
  }

  @Bean
  FacebookImportService facebookImportService(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    return new FacebookImportServiceImpl(newsEntryService, facebookConnector, messageSource);
  }

}
