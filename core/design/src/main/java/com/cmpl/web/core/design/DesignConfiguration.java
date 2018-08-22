package com.cmpl.web.core.design;

import com.cmpl.web.core.models.Design;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Design.class)
@EnableJpaRepositories(basePackageClasses = DesignRepository.class)
public class DesignConfiguration {

  @Bean
  public DesignDAO designDAO(DesignRepository designRepository,
      ApplicationEventPublisher publisher) {
    return new DefaultDesignDAO(designRepository, publisher);
  }

  @Bean
  public DesignMapper designMapper() {
    return new DesignMapper();
  }

  @Bean
  public DesignService designService(DesignDAO designDAO, DesignMapper designMapper) {
    return new DefaultDesignService(designDAO, designMapper);
  }

  @Bean
  public DesignTranslator designTranslator() {
    return new DefaultDesignTranslator();
  }

  @Bean
  public DesignDispatcher designDispatcher(DesignService designService,
      DesignTranslator designTranslator) {
    return new DefaultDesignDispatcher(designService, designTranslator);
  }

}
