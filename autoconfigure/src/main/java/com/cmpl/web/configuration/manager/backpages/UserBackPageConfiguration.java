package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBackPageConfiguration {

  @Bean
  BackPage viewUsers() {
    return BackPageBuilder.create().decorated(true).pageName("USER_VIEW")
        .templatePath("back/users/view_users").titleKey("back.users.title").build();
  }

  @Bean
  BackPage createUser() {
    return BackPageBuilder.create().decorated(true).pageName("USER_CREATE")
        .templatePath("back/users/create_user").titleKey("back.users.title").build();
  }

  @Bean
  BackPage updateUser() {
    return BackPageBuilder.create().decorated(true).pageName("USER_UPDATE")
        .templatePath("back/users/edit_user").titleKey("back.users.title").build();
  }

}
