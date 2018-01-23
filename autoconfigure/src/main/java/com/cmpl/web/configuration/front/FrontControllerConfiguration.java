package com.cmpl.web.configuration.front;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.front.ui.index.IndexController;
import com.cmpl.web.front.ui.media.MediaController;
import com.cmpl.web.front.ui.page.PageController;
import com.cmpl.web.front.ui.robot.RobotsController;
import com.cmpl.web.front.ui.sitemap.SitemapController;

@Configuration
public class FrontControllerConfiguration {

  @Bean
  public IndexController frontIndexController(DisplayFactory displayFactory) {
    return new IndexController(displayFactory);
  }

  @Bean
  public MediaController frontMediaController(MediaService mediaService){
    return new MediaController(mediaService);
  }

  @Bean
  public PageController frontPageController(DisplayFactory displayFactory){
    return new PageController(displayFactory);
  }

  @Bean
  public RobotsController robotsController(){
    return new RobotsController();
  }

  @Bean
  public SitemapController sitemapController(SitemapService sitemapService){
    return new SitemapController(sitemapService);
  }

}
