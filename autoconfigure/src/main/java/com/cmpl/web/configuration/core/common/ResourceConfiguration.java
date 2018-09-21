package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.message.DefaultWebMessageSource;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Configuration des cles i18n
 *
 * @author Louis
 */
@Configuration
public class ResourceConfiguration {

  /**
   * Declaration des sources de cles i18n
   */
  @Bean
  public WebMessageSource messageSource() throws IOException {
    DefaultWebMessageSource source = new DefaultWebMessageSource();
    Set<String> baseNames = computeBaseNames();
    source.setBasenames(baseNames.toArray(new String[baseNames.size()]));
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  private Set<String> computeBaseNames() throws IOException {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    return Lists.newArrayList(resolver.getResources("i18n/*.properties")).stream()
        .map(
            resource -> "i18n/" + resource.getFilename().replace(".properties", ""))
        .map(resource -> resource.replace("_en", ""))
        .map(resource -> resource.replace("_fr", ""))
        .collect(Collectors.toSet());

  }
}
