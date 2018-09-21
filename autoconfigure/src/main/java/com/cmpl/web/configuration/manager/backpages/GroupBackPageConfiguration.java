package com.cmpl.web.configuration.manager.backpages;

import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.BackPageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupBackPageConfiguration {

  @Bean
  BackPage viewGroups() {
    return BackPageBuilder.create().decorated(true).pageName("GROUP_VIEW")
        .templatePath("back/groups/view_groups").titleKey("back.groups.title").build();
  }

  @Bean
  BackPage createGroup() {
    return BackPageBuilder.create().decorated(true).pageName("GROUP_CREATE")
        .templatePath("back/groups/create_group").titleKey("back.groups.title").build();
  }

  @Bean
  BackPage updateGroup() {
    return BackPageBuilder.create().decorated(true).pageName("GROUP_UPDATE")
        .templatePath("back/groups/edit_group").titleKey("back.groups.title").build();
  }

}
