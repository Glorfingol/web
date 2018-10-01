package com.cmpl.web.core.website;

import com.cmpl.web.core.models.Website;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {Website.class})
@EnableJpaRepositories(basePackageClasses = {WebsiteRepository.class})
public class WebsiteConfiguration {

  @Bean
  public WebsiteMapper websiteMapper() {
    return new WebsiteMapper();
  }

  @Bean
  public WebsiteDAO websiteDAO(ApplicationEventPublisher publisher,
    WebsiteRepository websiteRepository) {
    return new DefaultWebsiteDAO(websiteRepository, publisher);
  }

  @Bean
  public WebsiteService websiteService(WebsiteDAO websiteDAO, WebsiteMapper websiteMapper) {
    return new DefaultWebsiteService(websiteDAO, websiteMapper);
  }

  @Bean
  public WebsiteTranslator websiteTranslator() {
    return new DefaultWebsiteTranslator();
  }

  @Bean
  public WebsiteDispatcher websiteDispatcher(WebsiteService websiteService,
    WebsiteTranslator websiteTranslator) {
    return new DefaultWebsiteDispatcher(websiteService, websiteTranslator);
  }

  @Bean
  public RenderingWebsiteMapper renderingWebsiteMapper() {
    return new RenderingWebsiteMapper();
  }

  @Bean
  public RenderingWebsiteService renderingWebsiteService(WebsiteDAO websiteDAO,
    RenderingWebsiteMapper renderingWebsiteMapper) {
    return new DefaultRenderingWebsiteService(websiteDAO, renderingWebsiteMapper);
  }
}
