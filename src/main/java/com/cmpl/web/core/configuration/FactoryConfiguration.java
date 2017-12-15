package com.cmpl.web.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.carousel.CarouselService;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSourceImpl;
import com.cmpl.web.news.NewsEntryService;
import com.cmpl.web.page.PageService;

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
      WebMessageSourceImpl messageSource, PageService pageService, NewsEntryService newsEntryService,
      ContextHolder contextHolder) {
    return new DisplayFactoryImpl(menuFactory, carouselService, messageSource, pageService, newsEntryService,
        contextHolder);
  }

}
