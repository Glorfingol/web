package com.cmpl.web.core.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@Configuration
@EnablePluginRegistries({BackMenuItemPlugin.class})
public class MenuConfiguration {
  

  @Autowired
  @Qualifier(value = "backMenus")
  private PluginRegistry<BackMenuItem, String> backMenus;

  @Bean
  public BackMenu backMenu() {
    return new BackMenu(backMenus);
  }

}
