package com.cmpl.web.configuration.modules;

import com.cmpl.web.configuration.modules.backup.BackupConfiguration;
import com.cmpl.web.configuration.modules.backup.BackupExportConfiguration;
import com.cmpl.web.configuration.modules.backup.BackupImportConfiguration;
import com.cmpl.web.configuration.modules.facebook.FacebookAutoConfiguration;
import com.cmpl.web.configuration.modules.facebook.FacebookConfiguration;
import com.cmpl.web.configuration.modules.google.GoogleConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

@Configuration
@Import({GoogleConfiguration.class, FacebookAutoConfiguration.class, FacebookConfiguration.class,
  BackupConfiguration.class, BackupExportConfiguration.class, BackupImportConfiguration.class})
public class ModulesConfiguration {


  @Value(value = "${baseUrl}")
  private String baseUrl;

  @Bean
  public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
    ConnectionRepository connectionRepository) {
    ConnectController controller = new ConnectController(connectionFactoryLocator,
      connectionRepository);
    controller.setApplicationUrl(baseUrl);
    return controller;

  }

}
