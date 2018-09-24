package com.cmpl.web.configuration.front;

import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.front.ui.blog.BlogController;
import com.cmpl.web.front.ui.index.IndexController;
import com.cmpl.web.front.ui.media.MediaController;
import com.cmpl.web.front.ui.robot.RobotsController;
import com.cmpl.web.front.ui.sitemap.RenderingSitemapController;
import com.cmpl.web.front.ui.website.RenderingWebsiteController;
import com.cmpl.web.front.ui.widgets.WidgetController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FrontControllerConfiguration {

  @Bean
  public IndexController frontIndexController(DisplayFactory displayFactory) {
    return new IndexController(displayFactory);
  }

  @Bean
  public MediaController frontMediaController(MediaService mediaService) {
    return new MediaController(mediaService);
  }

  @Bean
  public RenderingSitemapController sitemapController(
    RenderingSitemapService renderingSitemapService) {
    return new RenderingSitemapController(renderingSitemapService);
  }

  @Bean
  public BlogController blogController(DisplayFactory displayFactory) {
    return new BlogController(displayFactory);
  }

  @Bean
  public WidgetController widgetController(DisplayFactory displayFactory) {
    return new WidgetController(displayFactory);
  }

  @Bean
  public RenderingWebsiteController renderingWebsiteController(DisplayFactory displayFactory) {
    return new RenderingWebsiteController(displayFactory);
  }

  @Bean
  public RobotsController robotsController(WebsiteService websiteService) {
    return new RobotsController(websiteService);
  }

}
