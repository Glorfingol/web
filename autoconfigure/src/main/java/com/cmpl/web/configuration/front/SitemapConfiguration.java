package com.cmpl.web.configuration.front;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapServiceImpl;

@Configuration
public class SitemapConfiguration {

  @Bean
  public RenderingSitemapService sitemapService(MenuService menuService, WebMessageSource messageSource,
      ContextHolder contextHolder) {
    return new RenderingSitemapServiceImpl(messageSource, menuService, contextHolder);
  }
}
