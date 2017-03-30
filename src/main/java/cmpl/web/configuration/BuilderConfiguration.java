package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.FooterBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.builder.impl.FooterBuilderImpl;
import cmpl.web.builder.impl.MenuBuilderImpl;

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
}
