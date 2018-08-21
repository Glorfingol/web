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
public class StyleBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb styleBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).pageName("STYLE_VIEW")
        .build();
  }

  @Bean
  public BreadCrumb styleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).pageName("STYLE_UPDATE")
        .build();
  }

  @Bean
  public BreadCrumb styleCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).pageName("STYLE_CREATE")
        .build();
  }

  List<BreadCrumbItem> styleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(
        BreadCrumbItemBuilder.create().text("back.style.title").href("/manager/styles").build());
    return items;
  }
}
