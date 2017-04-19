package cmpl.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cmpl.web.message.impl.WebMessageSourceImpl;

@Configuration
public class ResourceConfiguration {

  @Bean
  public WebMessageSourceImpl messageSource() {
    WebMessageSourceImpl source = new WebMessageSourceImpl();
    source.setBasenames("i18n/pages", "i18n/menu", "i18n/footer", "i18n/keys", "i18n/back", "i18n/error", "i18n/form",
        "i18n/submenu");
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
  }

}
