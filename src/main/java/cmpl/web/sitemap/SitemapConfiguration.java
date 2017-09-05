package cmpl.web.sitemap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSource;
import cmpl.web.news.NewsEntryService;

@Configuration
public class SitemapConfiguration {

  @Bean
  SitemapService sitemapService(NewsEntryService newsEntryService, WebMessageSource messageSource) {
    return new SitemapServiceImpl(newsEntryService, messageSource);
  }
}
