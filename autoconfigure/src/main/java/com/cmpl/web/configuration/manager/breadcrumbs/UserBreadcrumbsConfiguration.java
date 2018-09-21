package com.cmpl.web.configuration.manager.breadcrumbs;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb userBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).pageName("USER_VIEW")
        .build();
  }

  @Bean
  public BreadCrumb userUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).pageName("USER_UPDATE")
        .build();
  }

  @Bean
  public BreadCrumb userCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).pageName("USER_CREATE")
        .build();
  }

  List<BreadCrumbItem> userBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(
        BreadCrumbItemBuilder.create().text("back.users.title").href("/manager/users").build());
    return items;
  }
}
