package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryRequestValidator;
import cmpl.web.news.NewsEntryRequestValidatorImpl;

/**
 * Configuration des validator de requetes
 * 
 * @author Louis
 *
 */
@Configuration
public class ValidatorConfiguration {

  @Bean
  NewsEntryRequestValidator newsEntryRequestValidator(WebMessageSourceImpl messageSource) {
    return NewsEntryRequestValidatorImpl.fromMessageSource(messageSource);
  }

}
