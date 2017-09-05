package cmpl.web.footer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.WebMessageSourceImpl;

@Configuration
public class FooterConfiguration {

  @Bean
  FooterFactory footerFactory(WebMessageSourceImpl messageSource) {
    return new FooterFactoryImpl(messageSource);
  }

}
