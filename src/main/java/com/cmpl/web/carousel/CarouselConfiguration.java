package com.cmpl.web.carousel;

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
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageService;

@Configuration
public class CarouselConfiguration {

  @Bean
  CarouselItemService carouselItemService(CarouselItemRepository carouselItemRepository, MediaService mediaService) {
    return new CarouselItemServiceImpl(carouselItemRepository, mediaService);
  }

  @Bean
  BackMenuItem carouselsBackMenuItem() {
    return BackMenuItemBuilder.create().href("back.carousels.href").label("back.carousels.label")
        .title("back.carousels.title").order(2).build();
  }

  @Bean
  BreadCrumb carouselBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_VIEW).build();
  }

  @Bean
  BreadCrumb carouselUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_UPDATE).build();
  }

  @Bean
  BreadCrumb carouselCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_CREATE).build();
  }

  List<BreadCrumbItem> carouselBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("Accueil").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("Gestion des carousels").href("/manager/carousels").build());
    return items;
  }

  @Bean
  CarouselService carouselService(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    return new CarouselServiceImpl(carouselRepository, carouselItemService);
  }

  @Bean
  CarouselTranslator carouselTranslator() {
    return new CarouselTranslatorImpl();
  }

  @Bean
  CarouselValidator carouselValidator(WebMessageSource messageSource) {
    return new CarouselValidatorImpl(messageSource);
  }

  @Bean
  CarouselManagerDisplayFactory carouselManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      CarouselService carouselService, CarouselItemService carouselItemService, PageService pageService,
      MediaService mediaService, ContextHolder contextHolder,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    return new CarouselManagerDisplayFactoryImpl(menuFactory, messageSource, carouselService, carouselItemService,
        pageService, mediaService, contextHolder, breadCrumbRegistry);
  }

  @Bean
  CarouselDispatcher carouselDispatcher(CarouselService carouselService, CarouselItemService carouselItemService,
      CarouselTranslator carouselTranslator, CarouselValidator carouselValidator, MediaService mediaService) {
    return new CarouselDispatcherImpl(carouselService, carouselItemService, mediaService, carouselTranslator,
        carouselValidator);
  }

}
