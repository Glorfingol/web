package cmpl.web.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.DisplayFactory;
import cmpl.web.core.factory.DisplayFactoryImpl;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryService;
import cmpl.web.page.PageService;

/**
 * Configuration des factory
 * 
 * @author Louis
 *
 */
@Configuration
public class FactoryConfiguration {

  @Bean
  DisplayFactory displayFactory(MenuFactory menuFactory, CarouselFactory carouselFactory,
      WebMessageSourceImpl messageSource, PageService pageService, NewsEntryService newsEntryService,
      ContextHolder contextHolder) {
    return new DisplayFactoryImpl(menuFactory, carouselFactory, messageSource, pageService, newsEntryService,
        contextHolder);
  }

}
