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
public class WebsiteBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb websiteBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).pageName("WEBSITE_VIEW")
        .build();
  }

  @Bean
  public BreadCrumb websiteUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).pageName("WEBSITE_UPDATE")
        .build();
  }

  @Bean
  public BreadCrumb websiteCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).pageName("WEBSITE_CREATE")
        .build();
  }

  List<BreadCrumbItem> websiteBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.websites.title").href("/manager/websites")
        .build());
    return items;
  }
}
