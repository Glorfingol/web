package cmpl.web.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.factory.DisplayFactory;
import cmpl.web.factory.impl.DisplayFactoryImpl;

@Configuration
public class FactoryConfiguration {

  @Bean
  DisplayFactory displayFactory(MenuBuilder menuBuilder, FooterBuilder footerBuilder, MessageSource messageSource) {
    return DisplayFactoryImpl.fromBuilders(menuBuilder, footerBuilder, messageSource);
  }

}
