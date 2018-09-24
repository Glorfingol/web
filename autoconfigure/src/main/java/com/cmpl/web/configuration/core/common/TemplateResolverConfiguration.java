package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.context.ContextHolder;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
public class TemplateResolverConfiguration {

  private final SpringTemplateEngine templateEngine;

  private final ContextHolder contextHolder;

  public TemplateResolverConfiguration(SpringTemplateEngine templateEngine,
    ContextHolder contextHolder) {
    this.templateEngine = Objects.requireNonNull(templateEngine);
    this.contextHolder = Objects.requireNonNull(contextHolder);
  }

  @PostConstruct
  public void extension() {
    FileTemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix(contextHolder.getTemplateBasePath());
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML");
    resolver.setOrder(templateEngine.getTemplateResolvers().size());
    resolver.setCheckExistence(true);
    templateEngine.addTemplateResolver(resolver);
  }

}
