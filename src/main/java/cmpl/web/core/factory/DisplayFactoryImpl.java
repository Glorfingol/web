package cmpl.web.core.factory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.carousel.CarouselDTO;
import cmpl.web.carousel.CarouselFactory;
import cmpl.web.carousel.CarouselItem;
import cmpl.web.core.context.ContextHolder;
import cmpl.web.core.model.PageWrapper;
import cmpl.web.footer.Footer;
import cmpl.web.footer.FooterFactory;
import cmpl.web.menu.MenuFactory;
import cmpl.web.menu.MenuItem;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.meta.MetaElementFactory;
import cmpl.web.meta.MetaElementToDelete;
import cmpl.web.news.NewsEntryDTO;
import cmpl.web.news.NewsEntryDisplayBean;
import cmpl.web.news.NewsEntryService;
import cmpl.web.page.PAGES;
import cmpl.web.page.PageDTO;
import cmpl.web.page.PageService;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public class DisplayFactoryImpl extends BaseDisplayFactoryImpl implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DisplayFactoryImpl.class);
  private final MenuFactory menuFactory;
  private final FooterFactory footerFactory;
  private final MetaElementFactory metaElementFactory;
  private final CarouselFactory carouselFactory;
  private final PageService pageService;
  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;

  public DisplayFactoryImpl(MenuFactory menuFactory, FooterFactory footerFactory,
      MetaElementFactory metaElementFactory, CarouselFactory carouselFactory, WebMessageSourceImpl messageSource,
      PageService pageService, NewsEntryService newsEntryService, ContextHolder contextHolder) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.footerFactory = footerFactory;
    this.metaElementFactory = metaElementFactory;
    this.carouselFactory = carouselFactory;
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.newsEntryService = newsEntryService;
  }

  @Override
  public ModelAndView computeModelAndViewForPage(PAGES page, Locale locale) {

    LOGGER.info("Construction de la page  " + page.name());
    ModelAndView model = new ModelAndView(computeDecoratorFrontTileName(locale));
    model.addObject("content", computeTileName(page.getTileName(), locale));

    LOGGER.info("Construction du menu pour la page " + page.name());
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + page.name());
    model.addObject("metaItems", computeMetaElements(locale, page));
    LOGGER.info("Construction des éléments meta open graph pour la page  " + page.name());
    model.addObject("openGraphMetaItems", computeOpenGraphMetaElements(locale, page));
    LOGGER.info("Construction du footer pour la page   " + page.name());
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + page.name());
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + page.name());
    model.addObject("hiddenLink", computeHiddenLink(locale));

    if (page.isWithCarousel()) {
      LOGGER.info("Construction du carousel pour la page " + page.name());
      model.addObject("carouselItems", computeCarouselItems(locale));
    }
    LOGGER.info("Page " + page.name() + " prête");

    return model;

  }

  public List<MenuItem> computeMenuItems(PAGES page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  public Footer computeFooter(Locale locale) {
    return footerFactory.computeFooter(locale);
  }

  public List<MetaElementToDelete> computeMetaElements(Locale locale, PAGES page) {
    return metaElementFactory.computeMetaElementsForPage(locale, page);
  }

  protected List<MetaElementToDelete> computeMetaElementsForNewsEntry(Locale locale, PAGES page, String newsEntryId) {
    return metaElementFactory.computeMetaElementsForNewsEntry(locale, page, newsEntryId);
  }

  protected List<MetaElementToDelete> computeOpenGraphMetaElementsForNewsEntry(Locale locale, PAGES page,
      String newsEntryId) {
    return metaElementFactory.computeOpenGraphMetaElementsNewsEntry(locale, page, newsEntryId);
  }

  List<MetaElementToDelete> computeOpenGraphMetaElements(Locale locale, PAGES page) {
    return metaElementFactory.computeOpenGraphMetaElementsForPage(locale, page);
  }

  List<CarouselItem> computeCarouselItems(Locale locale) {
    List<File> imagesForCarousel = computeCarouselImagesFiles(locale);
    return carouselFactory.computeCarouselItems(imagesForCarousel);
  }

  public List<File> computeCarouselImagesFiles(Locale locale) {

    String carouselImagesSrc = getI18nValue("carousel.src", locale);
    List<String> imagesSrcs = Arrays.asList(carouselImagesSrc.split(";"));
    List<File> imagesForCarousel = new ArrayList<>();
    ClassLoader classLoader = getClass().getClassLoader();
    for (String imageSrc : imagesSrcs) {
      URL resource = classLoader.getResource(imageSrc);
      if (resource != null && StringUtils.hasText(resource.getFile())) {
        File file = new File(resource.getFile());
        imagesForCarousel.add(file);
      } else {
        LOGGER.error("Image introuvable : " + imageSrc);
      }

    }
    return imagesForCarousel;
  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber) {
    LOGGER.info("Construction de la page  " + pageName);

    PageDTO page = pageService.getPageByName(pageName);

    ModelAndView model = new ModelAndView("decorator_poc");
    model.addObject("content", computePageContent(page));
    LOGGER.info("Construction du menu pour la page " + pageName);
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + pageName);
    model.addObject("metaItems", page.getMetaElements());
    LOGGER.info("Construction des éléments meta open graph pour la page  " + pageName);
    model.addObject("openGraphMetaItems", page.getOpenGraphMetaElements());
    LOGGER.info("Construction du footer pour la page   " + pageName);
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + pageName);
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));
    LOGGER.info("Construction des carousels pour la page " + pageName);
    List<CarouselDTO> carousels = computeCarouselsForPage(page);
    for (CarouselDTO carousel : carousels) {
      model.addObject("carousel_" + carousel.getName(), carousel);
    }
    if (page.isWithNews()) {
      LOGGER.info("Construction des entrées de blog pour la page " + pageName);
      PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = computePageWrapperOfNews(page, locale, pageNumber);
      model.addObject("wrappedNews", pagedNewsWrapped);
      model.addObject("emptyMessage", getI18nValue("actualites.empty", locale));
    }

    LOGGER.info("Page " + pageName + " prête");

    return model;
  }

  private List<CarouselDTO> computeCarouselsForPage(PageDTO page) {
    return carouselFactory.computeCarouselsForPage(String.valueOf(page.getId()));
  }

  private String computePageContent(PageDTO page) {
    return page.getName();
  }

  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  public PageWrapper<NewsEntryDisplayBean> computePageWrapperOfNews(PageDTO page, Locale locale, int pageNumber) {
    Page<NewsEntryDisplayBean> pagedNewsEntries = computeNewsEntries(page, locale, pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = new PageWrapper<>();
    pagedNewsWrapped.setCurrentPageNumber(currentPageNumber);
    pagedNewsWrapped.setFirstPage(isFirstPage);
    pagedNewsWrapped.setLastPage(isLastPage);
    pagedNewsWrapped.setPage(pagedNewsEntries);
    pagedNewsWrapped.setTotalPages(totalPages);
    pagedNewsWrapped.setPageBaseUrl("/pages/" + page.getName());
    pagedNewsWrapped.setPageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages));
    return pagedNewsWrapped;
  }

  public List<NewsEntryDisplayBean> computeNewsEntries(PageDTO page, Locale locale) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getEntities();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }

    for (NewsEntryDTO newsEntryFromDB : newsEntriesFromDB) {
      newsEntries.add(computeNewsEntryDisplayBean(page, locale, newsEntryFromDB));
    }

    return newsEntries;
  }

  public List<NewsEntryDisplayBean> computeNewsEntries(Locale locale) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    List<NewsEntryDTO> newsEntriesFromDB = newsEntryService.getEntities();
    if (CollectionUtils.isEmpty(newsEntriesFromDB)) {
      return newsEntries;
    }

    for (NewsEntryDTO newsEntryFromDB : newsEntriesFromDB) {
      newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntryFromDB));
    }

    return newsEntries;
  }

  public Page<NewsEntryDisplayBean> computeNewsEntries(PageDTO page, Locale locale, int pageNumber) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    PageRequest pageRequest = new PageRequest(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    for (NewsEntryDTO newsEntryFromDB : pagedNewsEntries.getContent()) {
      newsEntries.add(computeNewsEntryDisplayBean(page, locale, newsEntryFromDB));
    }

    return new PageImpl<>(newsEntries, pageRequest, pagedNewsEntries.getTotalElements());
  }

  public NewsEntryDisplayBean computeNewsEntry(Locale locale, String newsEntryId) {

    NewsEntryDTO newsEntryFromDB = newsEntryService.getEntity(Long.valueOf(newsEntryId));
    return computeNewsEntryDisplayBean(locale, newsEntryFromDB);
  }

  public NewsEntryDisplayBean computeNewsEntry(PageDTO page, Locale locale, String newsEntryId) {

    NewsEntryDTO newsEntryFromDB = newsEntryService.getEntity(Long.valueOf(newsEntryId));
    return computeNewsEntryDisplayBean(page, locale, newsEntryFromDB);
  }

  public NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);
    String labelAccroche = getI18nValue("news.entry.call", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat(), labelAccroche, "");
  }

  public NewsEntryDisplayBean computeNewsEntryDisplayBean(PageDTO page, Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);
    String labelAccroche = getI18nValue("news.entry.call", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat(), labelAccroche, "/pages/" + page.getName() + "/" + newsEntryDTO.getId());
  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, String entityId) {
    LOGGER.info("Construction de la page  " + pageName);

    PageDTO page = pageService.getPageByName(pageName);
    ModelAndView model = new ModelAndView("decorator_poc");
    model.addObject("content", computePageContent(page) + "_unique");
    LOGGER.info("Construction du menu pour la page " + pageName);
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction du footer pour la page   " + pageName);
    model.addObject("footer", computeFooter(locale));
    LOGGER.info("Construction du titre principal pour la page  " + pageName);
    model.addObject("mainTitle", computeMainTitle(locale));
    LOGGER.info("Construction du lien du back pour la page " + pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));
    LOGGER.info("Construction des carousels pour la page " + pageName);
    List<CarouselDTO> carousels = computeCarouselsForPage(page);
    for (CarouselDTO carousel : carousels) {
      model.addObject("carousel_" + carousel.getName(), carousel);
    }
    ModelAndView newsModelAndView = computeModelAndViewForPage(PAGES.NEWS_ENTRY, locale);
    LOGGER.info("Surcharge des meta elements pour la page de l'entree " + entityId);
    newsModelAndView.addObject("metaItems", computeMetaElementsForNewsEntry(locale, PAGES.NEWS_ENTRY, entityId));
    LOGGER.info("Surcharge des open graph meta elements pour la page de l'entree " + entityId);
    newsModelAndView.addObject("openGraphMetaItems",
        computeOpenGraphMetaElementsForNewsEntry(locale, PAGES.NEWS_ENTRY, entityId));
    LOGGER.info("Construction de l'entrée de blog pour la page " + PAGES.NEWS_ENTRY.name());
    newsModelAndView.addObject("newsEntry", computeNewsEntry(page, locale, entityId));

    return newsModelAndView;
  }

}
