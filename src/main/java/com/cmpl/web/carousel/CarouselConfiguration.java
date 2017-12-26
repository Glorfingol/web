package com.cmpl.web.carousel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementFactory;
import com.cmpl.web.page.PageService;

@Configuration
public class CarouselConfiguration {

  @Bean
  CarouselItemService carouselItemService(CarouselItemRepository carouselItemRepository, MediaService mediaService) {
    return new CarouselItemServiceImpl(carouselItemRepository, mediaService);
  }

  @Bean
  CarouselService carouselService(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    return new CarouselServiceImpl(carouselRepository, carouselItemService);
  }

  @Bean
  CarouselTranslator carouselTranslator() {
    return new CarouselTranslatorImpl();
  }

  @Bean
  CarouselValidator carouselValidator(WebMessageSource messageSource) {
    return new CarouselValidatorImpl(messageSource);
  }

  @Bean
  CarouselManagerDisplayFactory carouselManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MetaElementFactory metaElementFactory, CarouselService carouselService, CarouselItemService carouselItemService,
      PageService pageService, MediaService mediaService, ContextHolder contextHolder) {
    return new CarouselManagerDisplayFactoryImpl(menuFactory, messageSource, metaElementFactory, carouselService,
        carouselItemService, pageService, mediaService, contextHolder);
  }

  @Bean
  CarouselDispatcher carouselDispatcher(CarouselService carouselService, CarouselItemService carouselItemService,
      CarouselTranslator carouselTranslator, CarouselValidator carouselValidator, MediaService mediaService) {
    return new CarouselDispatcherImpl(carouselService, carouselItemService, mediaService, carouselTranslator,
        carouselValidator);
  }
}
