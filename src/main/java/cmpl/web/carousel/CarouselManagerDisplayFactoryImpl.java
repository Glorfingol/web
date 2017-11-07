package cmpl.web.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.factory.BackDisplayFactoryImpl;
import cmpl.web.core.model.PageWrapper;
import cmpl.web.media.MediaDTO;
import cmpl.web.media.MediaService;
import cmpl.web.menu.MenuFactory;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.page.BACK_PAGE;
import cmpl.web.page.PageDTO;
import cmpl.web.page.PageService;

public class CarouselManagerDisplayFactoryImpl extends BackDisplayFactoryImpl implements CarouselManagerDisplayFactory {

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

  public CarouselManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSourceImpl messageSource,
      MetaElementFactory metaElementFactory, CarouselService carouselService, CarouselItemService carouselItemService,
      PageService pageService, MediaService mediaService, ContextHolder contextHolder) {
    super(menuFactory, messageSource, metaElementFactory);
    this.carouselItemService = carouselItemService;
    this.carouselService = carouselService;
    this.contextHolder = contextHolder;
    this.pageService = pageService;
    this.mediaService = mediaService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllCarousels(BACK_PAGE backPage, Locale locale, int pageNumber) {
    ModelAndView carouselsManager = super.computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction des carousels pour la page " + backPage.name());

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = computePageWrapperOfCarousels(locale, pageNumber);

    carouselsManager.addObject("wrappedCarousels", pagedCarouselDTOWrapped);

    return carouselsManager;
  }

  PageWrapper<CarouselDTO> computePageWrapperOfCarousels(Locale locale, int pageNumber) {
    Page<CarouselDTO> pagedCarouselDTOEntries = computePagesEntries(pageNumber);

    boolean isFirstPage = pagedCarouselDTOEntries.isFirst();
    boolean isLastPage = pagedCarouselDTOEntries.isLast();
    int totalPages = pagedCarouselDTOEntries.getTotalPages();
    int currentPageNumber = pagedCarouselDTOEntries.getNumber();

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = new PageWrapper<>();
    pagedCarouselDTOWrapped.setCurrentPageNumber(currentPageNumber);
    pagedCarouselDTOWrapped.setFirstPage(isFirstPage);
    pagedCarouselDTOWrapped.setLastPage(isLastPage);
    pagedCarouselDTOWrapped.setPage(pagedCarouselDTOEntries);
    pagedCarouselDTOWrapped.setTotalPages(totalPages);
    pagedCarouselDTOWrapped.setPageBaseUrl("/manager/carousels");
    pagedCarouselDTOWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedCarouselDTOWrapped;
  }

  Page<CarouselDTO> computePagesEntries(int pageNumber) {
    List<CarouselDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<CarouselDTO> pagedCarouselDTOEntries = carouselService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedCarouselDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    for (CarouselDTO CarouselDTOFromDB : pagedCarouselDTOEntries.getContent()) {
      pageEntries.add(CarouselDTOFromDB);
    }

    return new PageImpl<>(pageEntries, pageRequest, pagedCarouselDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarousel(BACK_PAGE backPage, Locale locale, String carouselId) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(backPage, locale);
    CarouselDTO carousel = carouselService.getEntity(Long.parseLong(carouselId));
    carouselManager.addObject(UPDATE_FORM, createUpdateForm(carousel));
    List<PageDTO> pages = pageService.getEntities();
    carouselManager.addObject(PAGES, pages);
    return carouselManager;
  }

  private CarouselUpdateForm createUpdateForm(CarouselDTO carousel) {
    return new CarouselUpdateForm(carousel);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselMain(Locale locale, String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_main");
    CarouselDTO carousel = carouselService.getEntity(Long.parseLong(carouselId));
    carouselManager.addObject(UPDATE_FORM, createUpdateForm(carousel));
    List<PageDTO> pages = pageService.getEntities();
    carouselManager.addObject(PAGES, pages);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselItems(Locale locale, String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_items");
    carouselManager.addObject(CREATE_FORM, computeItemCreateForm(carouselId));
    List<MediaDTO> medias = mediaService.getEntities();
    carouselManager.addObject(MEDIAS, medias);
    List<CarouselItemDTO> items = carouselItemService.getByCarouselId(carouselId);
    carouselManager.addObject(ITEMS, items);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateCarousel(BACK_PAGE backPage, Locale locale) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(backPage, locale);
    carouselManager.addObject(CREATE_FORM, computeCreateForm());
    List<PageDTO> pages = pageService.getEntities();
    carouselManager.addObject(PAGES, pages);
    return carouselManager;
  }

  CarouselCreateForm computeCreateForm() {
    return new CarouselCreateForm();
  }

  CarouselItemCreateForm computeItemCreateForm(String carouselId) {
    CarouselItemCreateForm createForm = new CarouselItemCreateForm();
    createForm.setCarouselId(carouselId);
    return createForm;
  }

}
