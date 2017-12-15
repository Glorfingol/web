package com.cmpl.web.sitemap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.menu.MenuService;
import com.cmpl.web.message.WebMessageSource;

@Configuration
public class SitemapConfiguration {

  @Bean
  SitemapService sitemapService(MenuService menuService, WebMessageSource messageSource) {
    return new SitemapServiceImpl(messageSource, menuService);
  }
}
