package com.cmpl.web.core.common.context;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.StringUtils;

public class WebApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationContextInitializer.class);

  private static final String CONFIGURATION_FILE_KEY = "configurationFilePath";

  private static final String PROPERTIES_DOMAIN_NAME = "environment_specific_properties";


  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {

    String filePath = applicationContext.getEnvironment().getProperty(CONFIGURATION_FILE_KEY);
    if (!StringUtils.hasText(filePath)) {
      LOGGER.warn(
        "Le chemin du fichier de surcharge de configuration par environnement est vide on va donc charger la configuration de base dans le jar");
      return;
    }
    try {
      applicationContext.getEnvironment().getPropertySources()
        .addFirst(new PropertiesPropertySource(PROPERTIES_DOMAIN_NAME, loadPropertiesFromExternalFile(filePath)));
    } catch (IOException e) {
      LOGGER.warn("Impossible de charger la surcharge de configuration à partir du chemin : " + filePath
        + " on va donc charger la configuration de base dans le jar");
    }

  }


  private Properties loadPropertiesFromExternalFile(String filePath) throws IOException {
    Properties property = new Properties();
    property.load(new FileInputStream(filePath));
    return property;
  }

}
