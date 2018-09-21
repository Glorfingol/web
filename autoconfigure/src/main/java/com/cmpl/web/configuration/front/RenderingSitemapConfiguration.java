package com.cmpl.web.configuration.front;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.sitemap.rendering.DefaultRenderingSitemapService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;
import com.cmpl.web.core.website.WebsiteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RenderingSitemapConfiguration {

  @Bean
  public RenderingSitemapService renderingSitemapService(PageService pageService,
      WebMessageSource messageSource,
      WebsiteService websiteService, SitemapService sitemapService) {
    return new DefaultRenderingSitemapService(messageSource, pageService, websiteService,
        sitemapService);
  }

}