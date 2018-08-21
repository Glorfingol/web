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
public class MenuBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb menuBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).pageName("MENU_VIEW")
        .build();
  }

  @Bean
  public BreadCrumb menuUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).pageName("MENU_UPDATE")
        .build();
  }

  @Bean
  public BreadCrumb menuCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).pageName("MENU_CREATE")
        .build();
  }

  List<BreadCrumbItem> menuBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(
        BreadCrumbItemBuilder.create().text("back.menus.title").href("/manager/menus").build());
    return items;
  }
}
