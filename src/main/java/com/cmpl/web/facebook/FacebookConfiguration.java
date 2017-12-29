package com.cmpl.web.facebook;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.autoconfigure.FacebookProperties;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.news.NewsEntryService;

@Configuration
@EnableSocial
@PropertySource("classpath:/facebook/facebook.properties")
public class FacebookConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookConfiguration.class);

  @Value("${facebookConfigurationFile}")
  String facebookConfigurationFile;

  @Bean
  String facebookConfigurationFile() {
    return facebookConfigurationFile;
  }

  @Bean
  FacebookImportTranslator facebookImportTranslator() {
    return new FacebookImportTranslatorImpl();
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookService facebookService) {
    return new FacebookDisplayFactoryImpl(menuFactory, messageSource, facebookService);
  }

  @Bean
  FacebookDispatcher facebookDispatcher(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return new FacebookDispatcherImpl(facebookImportService, facebookImportTranslator);
  }

  @Bean
  FacebookService facebookService(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    return new FacebookServiceImpl(contextHolder, facebookConnector, connectionRepository, newsEntryService);
  }

  @Bean
  FacebookImportService facebookImportService(NewsEntryService newsEntryService, Facebook facebookConnector,
      WebMessageSource messageSource) {
    return new FacebookImportServiceImpl(newsEntryService, facebookConnector, messageSource);
  }

  @Configuration
  @EnableSocial
  @EnableConfigurationProperties(FacebookProperties.class)
  protected static class FacebookConfigurerAdapter extends SocialAutoConfigurerAdapter {

    private final String facebookConfigurationFile;

    protected FacebookConfigurerAdapter(String facebookConfigurationFile) {
      this.facebookConfigurationFile = facebookConfigurationFile;
    }

    @Bean
    @ConditionalOnMissingBean(Facebook.class)
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
      Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
      return connection != null ? connection.getApi() : null;
    }

    @Bean(name = {"connect/facebookConnect", "connect/facebookConnected"})
    @ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
    public GenericConnectionStatusView facebookConnectView() {
      return new GenericConnectionStatusView("facebook", "Facebook");
    }

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
      JSONParser parser = new JSONParser();
      Object obj;
      try {
        obj = parser.parse(new FileReader(facebookConfigurationFile));
        JSONObject jsonObject = (JSONObject) obj;
        String appId = (String) jsonObject.get("appId");
        String appSecret = (String) jsonObject.get("appSecret");
        return new FacebookConnectionFactory(appId, appSecret);
      } catch (Exception e) {
        LOGGER.error("Impossible d'initialiser Facebook Connect ", e);
      }
      return null;

    }

  }

}
