package com.cmpl.web.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.model.PageWrapper;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageDTO;
import com.cmpl.web.page.PageService;

public class CarouselManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<CarouselDTO> implements
    CarouselManagerDisplayFactory {

  private final CarouselService carouselService;
  private final PageService pageService;
  private final MediaService mediaService;
  private final CarouselItemService carouselItemService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String PAGES = "pages";
  private static final String MEDIAS = "medias";
  private static final String ITEMS = "items";

  public CarouselManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      CarouselService carouselService, CarouselItemService carouselItemService, PageService pageService,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry);
    this.carouselItemService = carouselItemService;
    this.carouselService = carouselService;
    this.contextHolder = contextHolder;
    this.pageService = pageService;
    this.mediaService = mediaService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllCarousels(Locale locale, int pageNumber) {
    ModelAndView carouselsManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_VIEW, locale);
    LOGGER.info("Construction des carousels pour la page " + BACK_PAGE.CAROUSELS_VIEW.name());

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = computePageWrapper(locale, pageNumber);

    carouselsManager.addObject("wrappedCarousels", pagedCarouselDTOWrapped);

    return carouselsManager;
  }

  @Override
  protected Page<CarouselDTO> computeEntries(Locale locale, int pageNumber) {
    List<CarouselDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<CarouselDTO> pagedCarouselDTOEntries = carouselService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedCarouselDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedCarouselDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedCarouselDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarousel(Locale locale, String carouselId) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_UPDATE, locale);
    return computeModelAndViewForCarouselUpdate(carouselManager, carouselId);
  }

  CarouselUpdateForm createUpdateForm(CarouselDTO carousel) {
    return new CarouselUpdateForm(carousel);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselMain(String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_main");
    return computeModelAndViewForCarouselUpdate(carouselManager, carouselId);
  }

  ModelAndView computeModelAndViewForCarouselUpdate(ModelAndView initializedModelAndView, String carouselId) {
    ModelAndView carouselManager = initializedModelAndView;
    CarouselDTO carousel = carouselService.getEntity(Long.parseLong(carouselId));
    carouselManager.addObject(UPDATE_FORM, createUpdateForm(carousel));

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(carousel.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) carouselManager.getModel().get("breadcrumb");
    if (breadCrumb != null) {
      if (canAddBreadCrumbItem(breadCrumb, item)) {
        breadCrumb.getItems().add(item);
      }
    }

    List<PageDTO> pages = pageService.getEntities();
    carouselManager.addObject(PAGES, pages);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselItems(String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_items");
    carouselManager.addObject(CREATE_FORM, computeItemCreateForm(carouselId));
    List<MediaDTO> medias = mediaService.getEntities();
    carouselManager.addObject(MEDIAS, medias);
    List<CarouselItemDTO> items = carouselItemService.getByCarouselId(carouselId);
    carouselManager.addObject(ITEMS, items);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateCarousel(Locale locale) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_CREATE, locale);
    carouselManager.addObject(CREATE_FORM, computeCreateForm());
    List<PageDTO> pages = pageService.getEntities();
    carouselManager.addObject(PAGES, pages);
    return carouselManager;
  }

  CarouselCreateForm computeCreateForm() {
    return CarouselCreateFormBuilder.create().build();
  }

  CarouselItemCreateForm computeItemCreateForm(String carouselId) {
    return CarouselItemCreateFormBuilder.create().carouselId(carouselId).build();
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/carousels";
  }

}
