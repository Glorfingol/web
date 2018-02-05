package com.cmpl.web.configuration.core.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

/**
 * Configuration des factory
 * 
 * @author Louis
 *
 */
@Configuration
public class FactoryConfiguration {

  @Bean
  DisplayFactory displayFactory(MenuFactory menuFactory, CarouselService carouselService,
      WebMessageSource messageSource, PageService pageService, NewsEntryService newsEntryService,
      ContextHolder contextHolder, WidgetPageService widgetPageService, WidgetService widgetService,
      MediaService mediaService) {
    return new DisplayFactoryImpl(menuFactory, carouselService, messageSource, pageService, newsEntryService,
        contextHolder, widgetPageService, widgetService, mediaService);
  }

}
