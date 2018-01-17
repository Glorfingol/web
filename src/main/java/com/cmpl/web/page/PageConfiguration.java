package com.cmpl.web.page;

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
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.meta.MetaElementService;
import com.cmpl.web.meta.OpenGraphMetaElementService;

@Configuration
public class PageConfiguration {

  @Bean
  PageManagerDisplayFactory pageManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PageService pageService, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new PageManagerDisplayFactoryImpl(menuFactory, messageSource, pageService, contextHolder,
        metaElementService, openGraphMetaElementService, breadCrumbs);
  }

  @Bean
  BackMenuItem pagesBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.pages.href").label("back.pages.label").title("back.pages.title")
        .order(1).iconClass("fa fa-code").build();
  }

  @Bean
  BreadCrumb pageBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_VIEW).build();
  }

  @Bean
  BreadCrumb pageUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_UPDATE).build();
  }

  @Bean
  BreadCrumb pageCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_CREATE).build();
  }

  List<BreadCrumbItem> pageBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.pages.title").href("back.pages.href").build());
    return items;
  }

  @Bean
  PageDispatcher pageDispatcher(PageValidator validator, PageTranslator translator, PageService pageService) {
    return new PageDispatcherImpl(validator, translator, pageService);
  }

  @Bean
  PageService pageService(PageRepository pageRepository, MetaElementService metaElementService,
      OpenGraphMetaElementService openGraphMetaElementService, FileService fileService) {
    return new PageServiceImpl(pageRepository, metaElementService, openGraphMetaElementService, fileService);
  }

  @Bean
  PageValidator pageValidator(WebMessageSource messageSource) {
    return new PageValidatorImpl(messageSource);
  }

  @Bean
  PageTranslator pageTranslator() {
    return new PageTranslatorImpl();
  }
}
