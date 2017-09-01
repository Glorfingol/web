package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.carousel.CarouselFactory;
import cmpl.web.carousel.CarouselFactoryImpl;
import cmpl.web.carousel.CarouselService;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.DisplayFactory;
import cmpl.web.core.factory.DisplayFactoryImpl;
import cmpl.web.facebook.FacebookDisplayFactory;
import cmpl.web.facebook.FacebookDisplayFactoryImpl;
import cmpl.web.facebook.FacebookService;
import cmpl.web.footer.FooterFactory;
import cmpl.web.footer.FooterFactoryImpl;
import cmpl.web.login.LoginDisplayFactory;
import cmpl.web.login.LoginDisplayFactoryImpl;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuFactoryImpl;
import cmpl.web.menu.MenuService;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementFactoryImpl;
import cmpl.web.news.NewsDisplayFactory;
import cmpl.web.news.NewsDisplayFactoryImpl;
import cmpl.web.news.NewsEntryService;
import cmpl.web.news.NewsManagerDisplayFactory;
import cmpl.web.news.NewsManagerDisplayFactoryImpl;
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
  DisplayFactory displayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      PageService pageService, NewsEntryService newsEntryService, ContextHolder contextHolder) {
    return DisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, metaElementFactory,
        carouselFactory, messageSource, pageService, newsEntryService, contextHolder);
  }

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return LoginDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageSource,
        metaElementFactory);
  }

  @Bean
  NewsDisplayFactory newsDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      FooterFactory footerFactory, MetaElementFactory metaElementFactory, CarouselFactory carouselFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, PageService pageService) {
    return NewsDisplayFactoryImpl.fromFactoriesAndMessageResourceAndServices(contextHolder, menuFactory, footerFactory,
        metaElementFactory, carouselFactory, messageSource, newsEntryService, pageService);
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      FooterFactory footerFactory, WebMessageSourceImpl messageSource, NewsEntryService newsEntryService,
      MetaElementFactory metaElementFactory) {
    return NewsManagerDisplayFactoryImpl.fromFactoriesAndMessageResource(contextHolder, menuFactory, footerFactory,
        messageSource, newsEntryService, metaElementFactory);
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, FacebookService facebookService, MetaElementFactory metaElementFactory) {
    return FacebookDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageSource,
        metaElementFactory, facebookService);
  }

  @Bean
  MenuFactory menuFactory(WebMessageSourceImpl messageSource, MenuService menuService) {
    return MenuFactoryImpl.fromMessageSource(messageSource, menuService);
  }

  @Bean
  FooterFactory footerFactory(WebMessageSourceImpl messageSource) {
    return FooterFactoryImpl.fromMessageSource(messageSource);
  }

  @Bean
  MetaElementFactory metaElementFactory(WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    return MetaElementFactoryImpl.fromMessageSource(messageSource, newsEntryService);
  }

  @Bean
  CarouselFactory carouselFactory(CarouselService carouselService) {
    return CarouselFactoryImpl.fromService(carouselService);
  }

}
