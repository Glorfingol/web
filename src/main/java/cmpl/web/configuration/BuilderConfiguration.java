package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.builder.impl.FooterBuilderImpl;
import cmpl.web.builder.impl.MenuBuilderImpl;
import cmpl.web.builder.impl.MetaElementBuilderImpl;

@Configuration
public class BuilderConfiguration {

  @Bean
  MenuBuilder menuBuilder(ResourceBundleMessageSource resourceBundleMessageSource) {
    return MenuBuilderImpl.fromResourceBundleMessageSource(resourceBundleMessageSource);
  }

  @Bean
  FooterBuilder footerBuilder(ResourceBundleMessageSource resourceBundleMessageSource) {
    return FooterBuilderImpl.fromResourceBundleMessageSource(resourceBundleMessageSource);
  }

  @Bean
  MetaElementBuilder metaElementBuilder(ResourceBundleMessageSource resourceBundleMessageSource) {
    return MetaElementBuilderImpl.fromResourceBundleMessageSource(resourceBundleMessageSource);
  }
}
