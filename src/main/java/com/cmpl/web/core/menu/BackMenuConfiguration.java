package com.cmpl.web.core.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@Configuration
@EnablePluginRegistries({BackMenuItemPlugin.class})
public class BackMenuConfiguration {

  @Autowired
  @Qualifier(value = "backMenus")
  private PluginRegistry<BackMenuItem, String> registry;

  @Bean
  BackMenu backMenu(@Qualifier(value = "backMenus") PluginRegistry<BackMenuItem, String> registry) {
    return new BackMenu(registry);
  }

  @Bean
  BackMenuItem indexBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.index.href").label("back.index.label").title("back.index.title")
        .order(0).build();
  }

}
