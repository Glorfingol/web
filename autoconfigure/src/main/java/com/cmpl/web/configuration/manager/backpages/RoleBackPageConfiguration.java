package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleBackPageConfiguration {

  @Bean
  BackPage viewRoles() {
    return BackPageBuilder.create().decorated(true).pageName("ROLE_VIEW")
        .templatePath("back/roles/view_roles").titleKey("back.roles.title").build();
  }

  @Bean
  BackPage createRole() {
    return BackPageBuilder.create().decorated(true).pageName("ROLE_CREATE")
        .templatePath("back/roles/create_role").titleKey("back.roles.title").build();
  }

  @Bean
  BackPage updateRole() {
    return BackPageBuilder.create().decorated(true).pageName("ROLE_UPDATE")
        .templatePath("back/roles/edit_role").titleKey("back.roles.title").build();
  }

}
