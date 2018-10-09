package com.cmpl.web.core.factory.carousel;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.carousel.CarouselCreateForm;
import com.cmpl.web.core.carousel.CarouselCreateFormBuilder;
import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.CarouselUpdateForm;
import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemCreateFormBuilder;
import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultCarouselManagerDisplayFactory extends AbstractBackDisplayFactory<CarouselDTO>
  implements CarouselManagerDisplayFactory {

  private final CarouselService carouselService;

  private final MediaService mediaService;

  private final CarouselItemService carouselItemService;

  private static final String CREATE_FORM = "createForm";

  private static final String UPDATE_FORM = "updateForm";

  private static final String MEDIAS = "medias";

  private static final String ITEMS = "items";

  public DefaultCarouselManagerDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    CarouselService carouselService, CarouselItemService carouselItemService,
    MediaService mediaService,
    ContextHolder contextHolder, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales,
    GroupService groupService, MembershipService membershipService,
    PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService,
      backPagesRegistry, contextHolder);

    this.carouselItemService = Objects.requireNonNull(carouselItemService);
    this.carouselService = Objects.requireNonNull(carouselService);
    this.mediaService = Objects.requireNonNull(mediaService);
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllCarousels(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("CAROUSEL_VIEW");
    ModelAndView carouselsManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des carousels pour la page {}", backPage.getPageName());

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = computePageWrapper(locale, pageNumber, "");

    carouselsManager.addObject("wrappedEntities", pagedCarouselDTOWrapped);

    return carouselsManager;
  }

  @Override
  protected Page<CarouselDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<CarouselDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<CarouselDTO> pagedCarouselDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedCarouselDTOEntries = carouselService.searchEntities(pageRequest, query);
    } else {
      pagedCarouselDTOEntries = carouselService.getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedCarouselDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedCarouselDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedCarouselDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarousel(Locale locale, String carouselId) {
    BackPage backPage = computeBackPage("CAROUSEL_UPDATE");
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(backPage, locale);
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

  ModelAndView computeModelAndViewForCarouselUpdate(ModelAndView initializedModelAndView,
    String carouselId) {
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
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselItems(String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_items");
    carouselManager.addObject(CREATE_FORM, computeItemCreateForm(carouselId));
    List<MediaDTO> medias = mediaService.getEntities();
    Collections.sort(medias, Comparator.comparing(MediaDTO::getName));
    carouselManager.addObject(MEDIAS, medias);
    List<CarouselItemDTO> items = carouselItemService.getByCarouselId(carouselId);
    carouselManager.addObject(ITEMS, items);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateCarousel(Locale locale) {
    BackPage backPage = computeBackPage("CAROUSEL_CREATE");
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(backPage, locale);
    carouselManager.addObject(CREATE_FORM, computeCreateForm());
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

  @Override
  protected String getItemLink() {
    return "/manager/carousels/";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/carousels/search";
  }

  @Override
  protected String getSearchPlaceHolder() {
    return "search.carousels.placeHolder";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:carousels:create";
  }

}
