package com.cmpl.web.configuration.core.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.*;
import com.cmpl.web.core.menu.*;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageService;

@Configuration
@EntityScan(basePackageClasses = Menu.class)
@EnableJpaRepositories(basePackageClasses = MenuRepository.class)
@EnablePluginRegistries({BackMenuItemPlugin.class})
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

  @Bean
  MenuWidgetProvider menuWidgetProvider(MenuFactory menuFactory, PageService pageService) {
    return new MenuWidgetProvider(menuFactory, pageService);
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
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, messageSource, menuService, pageService, contextHolder,
        breadCrumbs);
  }

  @Autowired
  @Qualifier(value = "backMenus")
  private PluginRegistry<BackMenuItem, String> backMenus;

  @Bean
  BackMenu backMenu() {
    return new BackMenu(backMenus);
  }

  @Bean
  BackMenuItem indexBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.index.href").label("back.index.label").title("back.index.title")
        .iconClass("fa fa-home").order(0).build();
  }
}
