package com.cmpl.web.configuration.modules.facebook;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.social.UserIdSource;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.autoconfigure.FacebookProperties;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.facebook.DoNothingFacebookAdapter;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.FacebookAdapterImpl;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.facebook.FacebookDispatcherImpl;
import com.cmpl.web.facebook.FacebookImportService;
import com.cmpl.web.facebook.FacebookImportServiceImpl;
import com.cmpl.web.facebook.FacebookImportTranslator;
import com.cmpl.web.facebook.FacebookImportTranslatorImpl;
import com.cmpl.web.facebook.FacebookService;
import com.cmpl.web.facebook.FacebookServiceImpl;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactoryImpl;

@Configuration
@EnableSocial
@PropertySource("classpath:/facebook/facebook.properties")
public class FacebookConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookConfiguration.class);

  @Value("${facebookConfigurationFile}")
  String facebookConfigurationFile;

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled")
  BackMenuItem facebookBackMenuItem() {
    return BackMenuItemBuilder.create().href("facebook.access.href").label("facebook.access.label")
        .title("facebook.access.title").iconClass("fa fa-facebook").order(7).build();
  }

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled")
  BreadCrumb facebookImportBreadCrumb() {
    return BreadCrumbBuilder.create().items(facebookImportBreadCrumbItems()).page(BACK_PAGE.FACEBOOK_IMPORT).build();
  }

  List<BreadCrumbItem> facebookImportBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("facebook.access.title").href("facebook.access.href").build());
    return items;
  }

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled")
  BreadCrumb facebookAccessBreadCrumb() {
    return BreadCrumbBuilder.create().items(facebookAccessBreadCrumbItems()).page(BACK_PAGE.FACEBOOK_ACCESS).build();
  }

  List<BreadCrumbItem> facebookAccessBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("facebook.access.label").href("facebook.access.href").build());
    return items;
  }

  @Bean
  FacebookProperties facebookProperties() {
    FacebookProperties properties = new FacebookProperties();
    JSONParser parser = new JSONParser();
    Object obj;
    try {
      obj = parser.parse(new FileReader(facebookConfigurationFile));
      JSONObject jsonObject = (JSONObject) obj;
      String appId = (String) jsonObject.get("appId");
      String appSecret = (String) jsonObject.get("appSecret");

      properties.setAppId(appId);
      properties.setAppSecret(appSecret);
    } catch (Exception e) {
      LOGGER.error("Impossible d'initialiser Facebook Connect ", e);
      properties.setAppId("mockId");
      properties.setAppSecret("mockSecret");
    }
    return properties;
  }

  @Bean
  FacebookImportTranslator facebookImportTranslator() {
    return new FacebookImportTranslatorImpl();
  }

  @Bean
  FacebookDisplayFactory facebookDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookAdapter facebookAdapter, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new FacebookDisplayFactoryImpl(menuFactory, messageSource, facebookAdapter, breadCrumbs);
  }

  @Bean
  FacebookDispatcher facebookDispatcher(FacebookImportService facebookImportService,
      FacebookImportTranslator facebookImportTranslator) {
    return new FacebookDispatcherImpl(facebookImportService, facebookImportTranslator);
  }

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled")
  FacebookService facebookService(ContextHolder contextHolder, Facebook facebookConnector,
      ConnectionRepository connectionRepository, NewsEntryService newsEntryService) {
    return new FacebookServiceImpl(contextHolder, facebookConnector, connectionRepository, newsEntryService);
  }

  @Bean
  FacebookImportService facebookImportService(NewsEntryService newsEntryService, FacebookAdapter facebookAdapter,
      WebMessageSource messageSource) {
    return new FacebookImportServiceImpl(newsEntryService, facebookAdapter, messageSource);
  }

  @Configuration
  @EnableSocial
  @EnableConfigurationProperties(FacebookProperties.class)
  protected static class FacebookConfigurerAdapter extends SocialAutoConfigurerAdapter {

    private final FacebookProperties facebookProperties;

    protected FacebookConfigurerAdapter(FacebookProperties facebookProperties) {
      this.facebookProperties = facebookProperties;
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
      return new FacebookConnectionFactory(facebookProperties.getAppId(), facebookProperties.getAppSecret());

    }

    @Override
    public UserIdSource getUserIdSource(){
      return new UserIdSource() {
        @Override
        public String getUserId() {
          return String.valueOf(UUID.randomUUID().getLeastSignificantBits());
        }
      };
    }

  }

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled")
  FacebookAdapter facebookAdapter(FacebookService facebookService, Facebook facebookConnector) {
    return new FacebookAdapterImpl(facebookService, facebookConnector);
  }

  @Bean
  @ConditionalOnProperty(prefix = "import.", name = "enabled", havingValue = "false")
  FacebookAdapter mockFacebookAdapter() {
    return new DoNothingFacebookAdapter();
  }

}
