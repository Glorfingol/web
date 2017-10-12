package cmpl.web.facebook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSource;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.news.NewsEntryService;

@Configuration
public class FacebookConfiguration {

  @Bean
  FacebookImportTranslator facebookImportTranslator() {
    return new FacebookImportTranslatorImpl();
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      FacebookService facebookService, MetaElementFactory metaElementFactory) {
    return new FacebookDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, facebookService);
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
