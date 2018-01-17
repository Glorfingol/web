package com.cmpl.web.style;

import java.util.ArrayList;
import java.util.List;

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
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;

@Configuration
public class StyleConfiguration {

  @Bean
  public StyleService styleService(StyleRepository styleRepository, MediaService mediaService, FileService fileService) {
    return new StyleServiceImpl(styleRepository, mediaService, fileService);
  }

  @Bean
  public StyleDispatcher styleDispacther(StyleService styleService, StyleTranslator styleTranslator) {
    return new StyleDispatcherImpl(styleService, styleTranslator);
  }

  @Bean
  BackMenuItem styleBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.style.href").label("back.style.label").title("back.style.title")
        .order(5).iconClass("fa fa-css3").build();
  }

  @Bean
  BreadCrumb styleBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_VIEW).build();
  }

  @Bean
  BreadCrumb styleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_UPDATE).build();
  }

  List<BreadCrumbItem> styleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.style.title").href("back.style.href").build());
    return items;
  }

  @Bean
  public StyleTranslator styleTranslator() {
    return new StyleTranslatorImpl();
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      StyleService styleService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new StyleDisplayFactoryImpl(menuFactory, messageSource, styleService, contextHolder, breadCrumbs);
  }
}
