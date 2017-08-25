package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.facebook.FacebookImportTranslator;
import cmpl.web.facebook.FacebookImportTranslatorImpl;
import cmpl.web.news.NewsEntryTranslator;
import cmpl.web.news.NewsEntryTranslatorImpl;

/**
 * Configuration des translator
 * 
 * @author Louis
 *
 */
@Configuration
public class TranslatorConfiguration {

  @Bean
  NewsEntryTranslator newsEntryTranslator() {
    return NewsEntryTranslatorImpl.fromVoid();
  }

  @Bean
  FacebookImportTranslator facebookImportTranslator() {
    return FacebookImportTranslatorImpl.fromVoid();
  }

}
