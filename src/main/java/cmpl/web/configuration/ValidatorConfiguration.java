package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.validator.NewsEntryRequestValidator;
import cmpl.web.validator.impl.NewsEntryRequestValidatorImpl;

@Configuration
public class ValidatorConfiguration {

  @Bean
  NewsEntryRequestValidator newsEntryRequestValidator(WebMessageSourceImpl messageSource) {
    return NewsEntryRequestValidatorImpl.fromMessageSource(messageSource);
  }

}
