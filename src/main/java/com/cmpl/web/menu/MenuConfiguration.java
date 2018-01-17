package com.cmpl.web.menu;

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
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageService;

@Configuration
public class MenuConfiguration {

  @Bean
  BackMenuItem menuBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.menus.href").label("back.menus.label").title("back.menus.title")
        .order(3).iconClass("fa fa-list-alt").build();
  }

  @Bean
  BreadCrumb menuBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_VIEW).build();
  }

  @Bean
  BreadCrumb menuUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_UPDATE).build();
  }

  @Bean
  BreadCrumb menuCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_CREATE).build();
  }

  List<BreadCrumbItem> menuBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.menus.title").href("back.menus.href").build());
    return items;
  }

  @Bean
  MenuService menuService(MenuRepository menuRepository) {
    return new MenuServiceImpl(menuRepository);
  }

  @Bean
  MenuFactory menuFactory(WebMessageSource messageSource, MenuService menuService, BackMenu backMenu) {
    return new MenuFactoryImpl(messageSource, menuService, backMenu);
  }

  @Bean
  MenuValidator menuValidator(WebMessageSource messageSource) {
    return new MenuValidatorImpl(messageSource);
  }

  @Bean
  MenuTranslator menuTranslator() {
    return new MenuTranslatorImpl();
  }

  @Bean
  MenuDispatcher menuDispatcher(MenuValidator validator, MenuTranslator translator, MenuService menuService,
      PageService pageService) {
    return new MenuDispatcherImpl(validator, translator, menuService, pageService);
  }

  @Bean
  MenuManagerDisplayFactory menuManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MenuService menuService, PageService pageService, ContextHolder contextHolder,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, messageSource, menuService, pageService, contextHolder,
        breadCrumbRegistry);
  }
}
