package com.cmpl.web.media;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

@Configuration
public class MediaConfiguration {

  @Bean
  public MediaService mediaService(MediaRepository mediaRepository, FileService fileService, ContextHolder contextHolder) {
    return new MediaServiceImpl(mediaRepository, fileService, contextHolder);
  }

  @Bean
  BackMenuItem mediasBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.medias.href").label("back.medias.label").title("back.medias.title")
        .order(4).iconClass("fa fa-file-image-o").build();
  }

  @Bean
  BreadCrumb mediaBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems()).page(BACK_PAGE.MEDIA_VIEW).build();
  }

  @Bean
  BreadCrumb mediaUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems()).page(BACK_PAGE.MEDIA_UPLOAD).build();
  }

  List<BreadCrumbItem> mediaBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.medias.title").href("back.medias.href").build());
    return items;
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    return new MediaManagerDisplayFactoryImpl(menuFactory, messageSource, mediaService, contextHolder,
        breadCrumbRegistry);
  }

}
