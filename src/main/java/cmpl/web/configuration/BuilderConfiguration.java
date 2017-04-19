package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.builder.impl.FooterBuilderImpl;
import cmpl.web.builder.impl.MenuBuilderImpl;
import cmpl.web.builder.impl.MetaElementBuilderImpl;
import cmpl.web.message.impl.WebMessageSourceImpl;

@Configuration
public class BuilderConfiguration {

  @Bean
  MenuBuilder menuBuilder(WebMessageSourceImpl messageSource) {
    return MenuBuilderImpl.fromMessageSource(messageSource);
  }

  @Bean
  FooterBuilder footerBuilder(WebMessageSourceImpl messageSource) {
    return FooterBuilderImpl.fromMessageSource(messageSource);
  }

  @Bean
  MetaElementBuilder metaElementBuilder(WebMessageSourceImpl messageSource) {
    return MetaElementBuilderImpl.fromMessageSource(messageSource);
  }
}
