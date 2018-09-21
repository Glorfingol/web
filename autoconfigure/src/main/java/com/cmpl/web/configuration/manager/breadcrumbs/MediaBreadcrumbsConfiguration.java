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
public class MediaBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb mediaBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems()).pageName("MEDIA_VIEW")
        .build();
  }

  @Bean
  public BreadCrumb mediaUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaEditBreadCrumb()).pageName("MEDIA_UPDATE")
        .build();
  }

  @Bean
  public BreadCrumb mediaCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems())
        .pageName("MEDIA_CREATE").build();
  }

  List<BreadCrumbItem> mediaBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(
        BreadCrumbItemBuilder.create().text("back.medias.title").href("/manager/medias").build());
    return items;
  }

  List<BreadCrumbItem> mediaEditBreadCrumb() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(
        BreadCrumbItemBuilder.create().text("back.medias.title").href("/manager/medias").build());
    items.add(BreadCrumbItemBuilder.create().text("back.medias.visualize.label").href("#").build());
    return items;
  }
}
