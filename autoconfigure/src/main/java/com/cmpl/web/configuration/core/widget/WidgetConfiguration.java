package com.cmpl.web.configuration.core.widget;

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
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.*;

@Configuration
@EntityScan(basePackageClasses = {Widget.class, WidgetPage.class})
@EnableJpaRepositories(basePackageClasses = {WidgetRepository.class, WidgetPageRepository.class})
public class WidgetConfiguration {

  @Bean
  BackMenuItem widgetBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.widgets.href").label("back.widgets.label")
        .title("back.widgets.title").iconClass("fa fa-cube").order(8).build();
  }

  @Bean
  BreadCrumb widgetBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_VIEW).build();
  }

  @Bean
  BreadCrumb widgetUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_UPDATE).build();
  }

  @Bean
  BreadCrumb widgetCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_CREATE).build();
  }

  List<BreadCrumbItem> widgetBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.widgets.title").href("back.widgets.href").build());
    return items;
  }

  @Bean
  WidgetService widgetService(WidgetRepository widgetRepository, FileService fileService) {
    return new WidgetServiceImpl(widgetRepository, fileService);
  }

  @Bean
  WidgetPageService widgetPageService(WidgetPageRepository widgetPageRepository) {
    return new WidgetPageServiceImpl(widgetPageRepository);
  }

  @Bean
  WidgetManagerDisplayFactory widgetManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      WidgetService widgetService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      PluginRegistry<WidgetProviderPlugin, String> widgetProviders) {
    return new WidgetManagerDisplayFactoryImpl(menuFactory, messageSource, contextHolder, widgetService, breadCrumbs,
        widgetProviders);
  }

  @Bean
  WidgetTranslator widgetTranslator() {
    return new WidgetTranslatorImpl();
  }

  @Bean
  WidgetValidator widgetValidator(WebMessageSource messageSource) {
    return new WidgetValidatorImpl(messageSource);
  }

  @Bean
  WidgetDispatcher widgetDispatcher(WidgetService widgetService, WidgetPageService widgetPageService,
      WidgetValidator widgetValidator, WidgetTranslator widgetTranslator) {
    return new WidgetDispatcherImpl(widgetTranslator, widgetValidator, widgetService, widgetPageService);
  }

}
