package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.factory.CarouselFactory;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.FacebookDisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.LoginDisplayFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.factory.impl.CarouselFactoryImpl;
import cmpl.web.factory.impl.DisplayFactoryImpl;
import cmpl.web.factory.impl.FacebookDisplayFactoryImpl;
import cmpl.web.factory.impl.FooterFactoryImpl;
import cmpl.web.factory.impl.LoginDisplayFactoryImpl;
import cmpl.web.factory.impl.MenuFactoryImpl;
import cmpl.web.factory.impl.MetaElementFactoryImpl;
import cmpl.web.factory.impl.NewsDisplayFactoryImpl;
import cmpl.web.factory.impl.NewsManagerDisplayFactoryImpl;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.service.FacebookService;
import cmpl.web.service.NewsEntryService;

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
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource) {
    return DisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, metaElementFactory,
        carouselFactory, messageSource);
  }

  @Bean
  LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementFactory) {
    return LoginDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageSource,
        metaElementFactory);
  }

  @Bean
  NewsDisplayFactory newsDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      NewsEntryService newsEntryService) {
    return NewsDisplayFactoryImpl.fromFactoriesAndMessageResourceAndServices(menuFactory, footerFactory,
        metaElementFactory, carouselFactory, messageSource, newsEntryService);
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementFactory) {
    return NewsManagerDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageSource,
        newsEntryService, metaElementFactory);
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, FooterFactory footerFactory,
      WebMessageSourceImpl messageSource, FacebookService facebookService, MetaElementFactory metaElementFactory) {
    return FacebookDisplayFactoryImpl.fromFactoriesAndMessageResource(menuFactory, footerFactory, messageSource,
        metaElementFactory, facebookService);
  }

  @Bean
  MenuFactory menuFactory(WebMessageSourceImpl messageSource) {
    return MenuFactoryImpl.fromMessageSource(messageSource);
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
  CarouselFactory carouselFactory() {
    return CarouselFactoryImpl.fromVoid();
  }

}
