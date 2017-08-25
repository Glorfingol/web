package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.facebook.FacebookDispatcher;
import cmpl.web.facebook.FacebookDispatcherImpl;
import cmpl.web.facebook.FacebookImportService;
import cmpl.web.facebook.FacebookImportTranslator;
import cmpl.web.news.NewsEntryDispatcher;
import cmpl.web.news.NewsEntryDispatcherImpl;
import cmpl.web.news.NewsEntryRequestValidator;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsEntryTranslator;

/**
 * Configuration des dispacther
 * 
 * @author Louis
 *
 */
@Configuration
public class DispatcherConfiguration {

  @Bean
  NewsEntryDispatcher newsEntryDispatcher(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService) {
    return NewsEntryDispatcherImpl.fromValidatorAndTranslator(validator, translator, newsEntryService);
  }

  @Bean
  FacebookDispatcher facebookDispatcher(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return FacebookDispatcherImpl.fromService(facebookImportService, facebookImportTranslator);
  }

}
