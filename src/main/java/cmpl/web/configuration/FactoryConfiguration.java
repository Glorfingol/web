package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.factory.BackDisplayFactory;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.NewsDisplayFactory;
import cmpl.web.factory.NewsManagerDisplayFactory;
import cmpl.web.factory.impl.BackDisplayFactoryImpl;
import cmpl.web.factory.impl.DisplayFactoryImpl;
import cmpl.web.factory.impl.NewsDisplayFactoryImpl;
import cmpl.web.factory.impl.NewsManagerDisplayFactoryImpl;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.service.NewsEntryService;

@Configuration
public class FactoryConfiguration {

  @Bean
  DisplayFactory displayFactory(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, WebMessageSourceImpl messageSource) {
    return DisplayFactoryImpl.fromBuilders(menuBuilder, footerBuilder, metaElementBuilder, messageSource);
  }

  @Bean
  BackDisplayFactory backdisplayFactory(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      WebMessageSourceImpl messageSource, MetaElementBuilder metaElementBuilder) {
    return BackDisplayFactoryImpl.fromBuilders(menuBuilder, footerBuilder, messageSource, metaElementBuilder);
  }

  @Bean
  NewsDisplayFactory newsDisplayFactory(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      MetaElementBuilder metaElementBuilder, WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    return NewsDisplayFactoryImpl.fromBuildersAndServices(menuBuilder, footerBuilder, metaElementBuilder,
        messageSource, newsEntryService);
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(MenuBuilder menuBuilder, FooterBuilder footerBuilder,
      WebMessageSourceImpl messageSource, NewsEntryService newsEntryService, MetaElementBuilder metaElementBuilder) {
    return NewsManagerDisplayFactoryImpl.fromBuilders(menuBuilder, footerBuilder, messageSource, newsEntryService,
        metaElementBuilder);
  }
}
