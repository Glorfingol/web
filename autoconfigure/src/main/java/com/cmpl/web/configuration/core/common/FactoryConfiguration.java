package com.cmpl.web.configuration.core.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageService;

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
      ContextHolder contextHolder, FileService fileService) {
    return new DisplayFactoryImpl(menuFactory, carouselService, messageSource, pageService, newsEntryService,
        contextHolder, fileService);
  }

}
