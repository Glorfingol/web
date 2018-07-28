package com.cmpl.web.core.sitemap;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.Design;

@Configuration
@EntityScan(basePackageClasses = Design.class)
@EnableJpaRepositories(basePackageClasses = SitemapRepository.class)
public class SitemapConfiguration {

  @Bean
  public SitemapDAO designDAO(SitemapRepository sitemapRepository, ApplicationEventPublisher publisher) {
    return new SitemapDAOImpl(sitemapRepository, publisher);
  }

  @Bean
  public SitemapMapper designMapper() {
    return new SitemapMapper();
  }

  @Bean
  public SitemapService designService(SitemapDAO sitemapDAO, SitemapMapper sitemapMapper) {
    return new SitemapServiceImpl(sitemapDAO, sitemapMapper);
  }

  @Bean
  public SitemapTranslator designTranslator() {
    return new SitemapTranslatorImpl();
  }

  @Bean
  public SitemapDispatcher designDispatcher(SitemapService sitemapService, SitemapTranslator sitemapTranslator) {
    return new SitemapDispatcherImpl(sitemapService, sitemapTranslator);
  }

}
