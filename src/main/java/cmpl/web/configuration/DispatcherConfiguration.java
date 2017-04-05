package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.dispatcher.NewsEntryDispatcher;
import cmpl.web.dispatcher.impl.NewsEntryDispatcherImpl;
import cmpl.web.service.NewsEntryService;
import cmpl.web.translator.NewsEntryTranslator;
import cmpl.web.validator.NewsEntryRequestValidator;

@Configuration
public class DispatcherConfiguration {

  @Bean
  NewsEntryDispatcher newsEntryDispatcher(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService) {
    return NewsEntryDispatcherImpl.fromValidatorAndTranslator(validator, translator, newsEntryService);
  }

}
