package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.FooterFactory;
import cmpl.web.factory.MenuFactory;
import cmpl.web.factory.MetaElementFactory;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.factory.impl.BackDisplayFactoryImpl;
import cmpl.web.factory.impl.DisplayFactoryImpl;
import cmpl.web.factory.impl.FooterFactoryImpl;
import cmpl.web.factory.impl.MenuFactoryImpl;
import cmpl.web.factory.impl.MetaElementFactoryImpl;
import cmpl.web.factory.impl.NewsDisplayFactoryImpl;
import cmpl.web.factory.impl.NewsManagerDisplayFactoryImpl;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.service.NewsEntryService;

@Configuration
public class FactoryConfiguration {

  @Bean
  DisplayFactory displayFactory(MenuFactory menuBuilder, FooterFactory footerBuilder,
      MetaElementFactory metaElementBuilder, WebMessageSourceImpl messageSource) {
    return DisplayFactoryImpl.fromFactoriesAndMessageResource(menuBuilder, footerBuilder, metaElementBuilder,
        messageSource);
  }

  @Bean
  BackDisplayFactory backdisplayFactory(MenuFactory menuBuilder, FooterFactory footerBuilder,
      WebMessageSourceImpl messageSource, MetaElementFactory metaElementBuilder) {
    return BackDisplayFactoryImpl.fromFactoriesAndMessageResource(menuBuilder, footerBuilder, messageSource,
        metaElementBuilder);
  }

  @Bean
  NewsDisplayFactory newsDisplayFactory(MenuFactory menuBuilder, FooterFactory footerBuilder,
      MetaElementFactory metaElementBuilder, WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    return NewsDisplayFactoryImpl.fromFactoriesAndMessageResourceAndServices(menuBuilder, footerBuilder,
        metaElementBuilder, messageSource, newsEntryService);
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(MenuFactory menuBuilder, FooterFactory footerBuilder,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementFactory metaElementBuilder) {
    return NewsManagerDisplayFactoryImpl.fromFactoriesAndMessageResource(menuBuilder, footerBuilder, messageSource,
        newsEntryService, metaElementBuilder);
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
  MetaElementFactory metaElementFactory(WebMessageSourceImpl messageSource) {
    return MetaElementFactoryImpl.fromMessageSource(messageSource);
  }
}
