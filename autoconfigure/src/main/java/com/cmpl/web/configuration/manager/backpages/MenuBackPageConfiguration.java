package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuBackPageConfiguration {

  @Bean
  BackPage vieMenus() {
    return BackPageBuilder.create().decorated(true).pageName("MENU_VIEW")
        .templatePath("back/menus/view_menus").titleKey("back.menus.title").build();
  }

  @Bean
  BackPage createMenu() {
    return BackPageBuilder.create().decorated(true).pageName("MENU_CREATE")
        .templatePath("back/menus/create_menu").titleKey("back.menus.title").build();
  }

  @Bean
  BackPage updateMenu() {
    return BackPageBuilder.create().decorated(true).pageName("MENU_UPDATE")
        .templatePath("back/menus/edit_menu").titleKey("back.menus.title").build();
  }

}
