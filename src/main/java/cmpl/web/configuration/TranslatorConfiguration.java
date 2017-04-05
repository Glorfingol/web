package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.translator.impl.NewsEntryTranslatorImpl;

@Configuration
public class TranslatorConfiguration {

  @Bean
  NewsEntryTranslator newsEntryTranslator() {
    return NewsEntryTranslatorImpl.fromVoid();
  }

}
