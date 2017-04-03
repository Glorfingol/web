package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceConfiguration {

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/pages", "i18n/menu", "i18n/footer");
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
  }

}
