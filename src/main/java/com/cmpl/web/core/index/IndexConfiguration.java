package com.cmpl.web.core.index;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.page.BACK_PAGE;

@Configuration
public class IndexConfiguration {

  @Bean
  BreadCrumb indexBreadCrumb() {
    return BreadCrumbBuilder.create().items(indexBreadCrumbItems()).page(BACK_PAGE.INDEX).build();
  }

  List<BreadCrumbItem> indexBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("Accueil").href("/manager/").build());
    return items;
  }

}
