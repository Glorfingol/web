package com.cmpl.web.configuration.core.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.page.PageDispatcher;
import com.cmpl.web.core.page.PageDispatcherImpl;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.page.PageServiceImpl;
import com.cmpl.web.core.page.PageTranslator;
import com.cmpl.web.core.page.PageTranslatorImpl;
import com.cmpl.web.core.page.PageValidator;
import com.cmpl.web.core.page.PageValidatorImpl;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@Configuration
@EntityScan(basePackageClasses = Page.class)
@EnableJpaRepositories(basePackageClasses = PageRepository.class)
public class PageConfiguration {

  @Bean
  PageManagerDisplayFactory pageManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PageService pageService, WidgetService widgetService,
      WidgetPageService widgetPageService, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new PageManagerDisplayFactoryImpl(menuFactory, messageSource, pageService, contextHolder, widgetService,
        widgetPageService, breadCrumbs);
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
  PageService pageService(PageRepository pageRepository, FileService fileService) {
    return new PageServiceImpl(pageRepository, fileService);
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
