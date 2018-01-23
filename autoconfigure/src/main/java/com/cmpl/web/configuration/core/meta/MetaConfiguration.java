package com.cmpl.web.configuration.core.meta;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.meta.MetaElement;
import com.cmpl.web.core.meta.MetaElementDispatcher;
import com.cmpl.web.core.meta.MetaElementDispatcherImpl;
import com.cmpl.web.core.meta.MetaElementRepository;
import com.cmpl.web.core.meta.MetaElementService;
import com.cmpl.web.core.meta.MetaElementServiceImpl;
import com.cmpl.web.core.meta.MetaElementTranslator;
import com.cmpl.web.core.meta.MetaElementTranslatorImpl;
import com.cmpl.web.core.meta.MetaElementValidator;
import com.cmpl.web.core.meta.MetaElementValidatorImpl;
import com.cmpl.web.core.meta.OpenGraphMetaElement;
import com.cmpl.web.core.meta.OpenGraphMetaElementRepository;
import com.cmpl.web.core.meta.OpenGraphMetaElementService;
import com.cmpl.web.core.meta.OpenGraphMetaElementServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {MetaElement.class, OpenGraphMetaElement.class})
@EnableJpaRepositories(basePackageClasses = {MetaElementRepository.class,OpenGraphMetaElementRepository.class})
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
