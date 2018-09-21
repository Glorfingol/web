package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import com.cmpl.web.core.page.BackPagePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@Configuration
@EnablePluginRegistries({BackPagePlugin.class})
@Import({AdministrationBackPageConfiguration.class, WebMasteringBackPageConfiguration.class})
public class BackPagesConfiguration {

  @Autowired
  @Qualifier(value = "backPages")
  private PluginRegistry<BreadCrumb, String> backPages;

  @Bean
  BackPage index() {
    return BackPageBuilder.create().decorated(true).pageName("INDEX")
        .templatePath("back/index").titleKey("back.index.title").build();
  }

  @Bean
  BackPage login() {
    return BackPageBuilder.create().decorated(false).pageName("LOGIN")
        .templatePath("back/login").titleKey("").build();
  }

  @Bean
  BackPage forgottenPassword() {
    return BackPageBuilder.create().decorated(false).pageName("FORGOTTEN_PASSWORD")
        .templatePath("back/forgotten_password").titleKey("").build();
  }


  @Bean
  BackPage changePassword() {
    return BackPageBuilder.create().decorated(false).pageName("CHANGE_PASSWORD")
        .templatePath("back/change_password").titleKey("").build();
  }


}
