package com.cmpl.web.configuration.core.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.carousel.Carousel;
import com.cmpl.web.core.carousel.CarouselDAO;
import com.cmpl.web.core.carousel.CarouselDAOImpl;
import com.cmpl.web.core.carousel.CarouselDispatcher;
import com.cmpl.web.core.carousel.CarouselDispatcherImpl;
import com.cmpl.web.core.carousel.CarouselMapper;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.CarouselServiceImpl;
import com.cmpl.web.core.carousel.CarouselTranslator;
import com.cmpl.web.core.carousel.CarouselTranslatorImpl;
import com.cmpl.web.core.carousel.CarouselValidator;
import com.cmpl.web.core.carousel.CarouselValidatorImpl;
import com.cmpl.web.core.carousel.item.CarouselItem;
import com.cmpl.web.core.carousel.item.CarouselItemDAO;
import com.cmpl.web.core.carousel.item.CarouselItemDAOImpl;
import com.cmpl.web.core.carousel.item.CarouselItemMapper;
import com.cmpl.web.core.carousel.item.CarouselItemRepository;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.carousel.item.CarouselItemServiceImpl;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.carousel.CarouselWidgetProvider;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EntityScan(basePackageClasses = {Carousel.class, CarouselItem.class})
@EnableJpaRepositories(basePackageClasses = {CarouselRepository.class, CarouselItemRepository.class})
public class CarouselConfiguration {

  @Bean
  public CarouselItemDAO carouselItemDAO(CarouselItemRepository carouselItemRepository,
      ApplicationEventPublisher publisher) {
    return new CarouselItemDAOImpl(carouselItemRepository, publisher);
  }

  @Bean
  public CarouselItemMapper carouselItemMapper(MediaService mediaService) {
    return new CarouselItemMapper(mediaService);
  }

  @Bean
  public CarouselItemService carouselItemService(CarouselItemDAO carouselItemDAO,
      CarouselItemMapper carouselItemMapper) {
    return new CarouselItemServiceImpl(carouselItemDAO, carouselItemMapper);
  }

  @Bean
  public BackMenuItem carouselsBackMenuItem(BackMenuItem webmastering, Privilege carouselsReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.carousels.href").label("back.carousels.label")
        .title("back.carousels.title").iconClass("fa fa-files-o").parent(webmastering).order(2)
        .privilege(carouselsReadPrivilege.privilege()).build();
  }

  @Bean
  public BreadCrumb carouselBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_VIEW).build();
  }

  @Bean
  public BreadCrumb carouselUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_UPDATE).build();
  }

  @Bean
  public CarouselWidgetProvider carouselWidgetProvider(CarouselService carouselService) {
    return new CarouselWidgetProvider(carouselService);
  }

  @Bean
  public BreadCrumb carouselCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_CREATE).build();
  }

  List<BreadCrumbItem> carouselBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.carousels.title").href("back.carousels.href").build());
    return items;
  }

  @Bean
  public CarouselDAO carouselDAO(CarouselRepository carouselRepository, ApplicationEventPublisher publisher) {
    return new CarouselDAOImpl(carouselRepository, publisher);
  }

  @Bean
  public CarouselMapper carouselMapper(CarouselItemService carouselItemService) {
    return new CarouselMapper(carouselItemService);
  }

  @Bean
  public CarouselService carouselService(CarouselDAO carouselDAO, CarouselMapper carouselMapper) {
    return new CarouselServiceImpl(carouselDAO, carouselMapper);
  }

  @Bean
  public CarouselTranslator carouselTranslator() {
    return new CarouselTranslatorImpl();
  }

  @Bean
  public CarouselValidator carouselValidator(WebMessageSource messageSource) {
    return new CarouselValidatorImpl(messageSource);
  }

  @Bean
  public CarouselManagerDisplayFactory carouselManagerDisplayFactory(MenuFactory menuFactory,
      WebMessageSource messageSource, CarouselService carouselService, CarouselItemService carouselItemService,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales) {
    return new CarouselManagerDisplayFactoryImpl(menuFactory, messageSource, carouselService, carouselItemService,
        mediaService, contextHolder, breadCrumbs, availableLocales);
  }

  @Bean
  public CarouselDispatcher carouselDispatcher(CarouselService carouselService, CarouselItemService carouselItemService,
      CarouselTranslator carouselTranslator, CarouselValidator carouselValidator, MediaService mediaService) {
    return new CarouselDispatcherImpl(carouselService, carouselItemService, mediaService, carouselTranslator,
        carouselValidator);
  }

}
