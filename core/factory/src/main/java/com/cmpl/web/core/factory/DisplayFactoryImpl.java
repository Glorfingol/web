package com.cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDisplayBean;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = {"modelPage"})
public class DisplayFactoryImpl extends BaseDisplayFactoryImpl implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DisplayFactoryImpl.class);
  private final MenuFactory menuFactory;
  private final CarouselService carouselService;
  private final PageService pageService;
  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;
  private final FileService fileService;

  public DisplayFactoryImpl(MenuFactory menuFactory, CarouselService carouselService, WebMessageSource messageSource,
      PageService pageService, NewsEntryService newsEntryService, ContextHolder contextHolder, FileService fileService) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.carouselService = carouselService;
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.newsEntryService = newsEntryService;
    this.fileService = fileService;
  }

  @Override
  @Cacheable(sync = true)
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber) {
    LOGGER.info("Construction de la page  " + pageName);

    PageDTO page = pageService.getPageByName(pageName);

    ModelAndView model = new ModelAndView("decorator");
    model.addObject("content", computePageContent(page));
    LOGGER.info("Construction du menu pour la page " + pageName);
    model.addObject("menuItems", computeMenuItems(page, locale));
    LOGGER.info("Construction des éléments meta pour la page  " + pageName);
    model.addObject("metaItems", page.getMetaElements());
    LOGGER.info("Construction des éléments meta open graph pour la page  " + pageName);
    model.addObject("openGraphMetaItems", page.getOpenGraphMetaElements());
    LOGGER.info("Construction du footer pour la page   " + pageName);
    model.addObject("footerTemplate", computePageFooter(page));
    LOGGER.info("Construction du header pour la page   " + pageName);
    model.addObject("header", computePageHeader(page));
    LOGGER.info("Construction du lien du back pour la page " + pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));
    LOGGER.info("Construction des carousels pour la page " + pageName);
    List<CarouselDTO> carousels = computeCarouselsForPage(page);
    carousels.forEach(carousel -> model.addObject("carousel_" + carousel.getName(), carousel));
    if (page.isWithNews()) {

      model.addObject("news_entries","widgets/blog");
      LOGGER.info("Construction des entrées de blog pour la page " + pageName);
      PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = computePageWrapperOfNews(page, locale, pageNumber);

      List<NewsEntryDTO> entries = computeNewsEntriesForPage(pageNumber);
      List<String> entriesIds = new ArrayList<>();
      entries.forEach(entry -> entriesIds.add(String.valueOf(entry.getId())));

      model.addObject("wrappedNews", pagedNewsWrapped);
      model.addObject("news", entriesIds);
      model.addObject("emptyMessage", getI18nValue("actualites.empty", locale));
    }

    LOGGER.info("Page " + pageName + " prête");

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForBlogEntry(String newsEntryId, Locale locale) {

    LOGGER.info("Construction de l'entree de blog d'id " + newsEntryId);

    ModelAndView model = new ModelAndView(computeNewsTemplate());
    NewsEntryDisplayBean newsEntryDisplayBean = computeNewsEntryDisplayBean(locale,newsEntryService.getEntity(Long.parseLong(newsEntryId)));
    model.addObject("newsBean",newsEntryDisplayBean);

    LOGGER.info("Entree de blog " + newsEntryId + " prête");


    return model;
  }

  List<CarouselDTO> computeCarouselsForPage(PageDTO page) {
    return carouselService.findByPageId(String.valueOf(page.getId()));
  }

  String computePageContent(PageDTO page) {
    return page.getName();
  }

  String computePageHeader(PageDTO page) {
    return page.getName() + "_header";
  }

  String computeNewsTemplate() {
    if (!doesTemplateExist("news_entry.html")) {
      return "pages/actualites/news_entry";
    }
    return "news_entry";
  }

  String computePageFooter(PageDTO page) {
    return page.getName() + "_footer";
  }

  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  public PageWrapper<NewsEntryDisplayBean> computePageWrapperOfNews(PageDTO page, Locale locale, int pageNumber) {
    Page<NewsEntryDisplayBean> pagedNewsEntries = computeNewsEntries( locale, pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    return new PageWrapperBuilder<NewsEntryDisplayBean>().currentPageNumber(currentPageNumber).firstPage(isFirstPage)
        .lastPage(isLastPage).page(pagedNewsEntries).totalPages(totalPages).pageBaseUrl("/pages/" + page.getName())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  public Page<NewsEntryDisplayBean> computeNewsEntries( Locale locale, int pageNumber) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    pagedNewsEntries.getContent().forEach(
        newsEntry -> newsEntries.add(computeNewsEntryDisplayBean( locale, newsEntry)));

    return new PageImpl<>(newsEntries, pageRequest, pagedNewsEntries.getTotalElements());
  }

  public NewsEntryDisplayBean computeNewsEntry( Locale locale, String newsEntryId) {

    NewsEntryDTO newsEntryFromDB = newsEntryService.getEntity(Long.valueOf(newsEntryId));
    return computeNewsEntryDisplayBean( locale, newsEntryFromDB);
  }

  public NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat());
  }

  private boolean doesTemplateExist(String templateName) {
    return fileService.readFileContentFromSystem(templateName) != null;
  }

  private List<NewsEntryDTO> computeNewsEntriesForPage(int pageNumber){


    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);

    return pagedNewsEntries.getContent();

  }

}
