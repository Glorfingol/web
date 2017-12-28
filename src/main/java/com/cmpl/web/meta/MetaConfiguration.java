package com.cmpl.web.meta;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.message.WebMessageSource;

@Configuration
public class MetaConfiguration {

  @Bean
  MetaElementService metaElementService(MetaElementRepository repository) {
    return new MetaElementServiceImpl(repository);
  }

  @Bean
  OpenGraphMetaElementService openGraphMetaElementService(OpenGraphMetaElementRepository repository) {
    return new OpenGraphMetaElementServiceImpl(repository);
  }

  @Bean
  MetaElementDispatcher metaElementDispatcher(MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, MetaElementTranslator translator,
      MetaElementValidator validator) {
    return new MetaElementDispatcherImpl(metaElementService, openGraphMetaElementService, translator, validator);
  }

  @Bean
  MetaElementValidator metaElementValidator(WebMessageSource messageSource) {
    return new MetaElementValidatorImpl(messageSource);
  }

  @Bean
  MetaElementTranslator metaElementTranslator() {
    return new MetaElementTranslatorImpl();
  }
}
