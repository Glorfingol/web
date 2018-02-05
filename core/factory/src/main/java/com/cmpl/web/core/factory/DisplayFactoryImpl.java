package com.cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDisplayBean;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.widget.WIDGET_TYPE;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetPageDTO;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public class DisplayFactoryImpl extends BaseDisplayFactoryImpl implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DisplayFactoryImpl.class);
  private final MenuFactory menuFactory;
  private final CarouselService carouselService;
  private final PageService pageService;
  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;
  private final WidgetPageService widgetPageService;
  private final WidgetService widgetService;
  private final MediaService mediaService;

  public DisplayFactoryImpl(MenuFactory menuFactory, CarouselService carouselService, WebMessageSource messageSource,
      PageService pageService, NewsEntryService newsEntryService, ContextHolder contextHolder,
      WidgetPageService widgetPageService, WidgetService widgetService, MediaService mediaService) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.carouselService = carouselService;
    this.pageService = pageService;
    this.contextHolder = contextHolder;
    this.newsEntryService = newsEntryService;
    this.widgetPageService = widgetPageService;
    this.widgetService = widgetService;
    this.mediaService = mediaService;
  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber) {
    LOGGER.info("Construction de la page  {}", pageName);

    PageDTO page = pageService.getPageByName(pageName);

    ModelAndView model = new ModelAndView("decorator");
    model.addObject("content", computePageContent(page));
    LOGGER.info("Construction des éléments meta pour la page  {}", pageName);
    model.addObject("metaItems", page.getMetaElements());
    LOGGER.info("Construction des éléments meta open graph pour la page {}", pageName);
    model.addObject("openGraphMetaItems", page.getOpenGraphMetaElements());
    LOGGER.info("Construction du footer pour la page  {}", pageName);
    model.addObject("footerTemplate", computePageFooter(page));
    LOGGER.info("Construction du header pour la page  {}", pageName);
    model.addObject("header", computePageHeader(page));
    LOGGER.info("Construction du lien du back pour la page {}", pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.info("Construction des widgets pour la page {}", pageName);
    List<WidgetPageDTO> widgetPageDTOS = widgetPageService.findByPageId(String.valueOf(page.getId()));
    List<String> widgetIds = new ArrayList<>();
    widgetPageDTOS.forEach(widgetPageDTO -> widgetIds.add(widgetPageDTO.getWidgetId()));
    List<String> widgetNames = new ArrayList<>();
    widgetIds.forEach(widgetId -> widgetNames.add(widgetService.getEntity(Long.parseLong(widgetId)).getName()));

    model.addObject("widgetNames", widgetNames);

    LOGGER.info("Page {0} prête", pageName);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForBlogEntry(String newsEntryId, String widgetId, Locale locale) {

    LOGGER.info("Construction de l'entree de blog d'id {}", newsEntryId);

    ModelAndView model = new ModelAndView(computeNewsTemplate(widgetId));
    NewsEntryDisplayBean newsEntryDisplayBean = computeNewsEntryDisplayBean(locale,
        newsEntryService.getEntity(Long.parseLong(newsEntryId)));
    model.addObject("newsBean", newsEntryDisplayBean);

    LOGGER.info("Entree de blog {}  prête", newsEntryId);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber, String pageName) {

    LOGGER.info("Construction du wiget {}", widgetName);

    WidgetDTO widget = widgetService.findByName(widgetName);
    ModelAndView model = new ModelAndView(computeWidgetTemplate(widget));

    model.addObject("pageNumber", pageNumber);

    Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale, pageName);
    if (!CollectionUtils.isEmpty(widgetModel)) {
      widgetModel.forEach((key, value) -> model.addObject(key, value));
    }
    model.addObject("widgetName", widget.getName());

    LOGGER.info("Widget {} prêt", widgetName);

    return model;
  }

  String computeWidgetTemplate(WidgetDTO widget) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "";
    }
    WIDGET_TYPE widgetType = widget.getType();

    if (WIDGET_TYPE.BLOG.equals(widgetType)) {
      return "widgets/blog";
    }
    if (WIDGET_TYPE.CAROUSEL.equals(widgetType)) {
      return "widgets/carousel";
    }
    if (WIDGET_TYPE.HTML.equals(widgetType)) {
      return "widgets/default";
    }
    if (WIDGET_TYPE.IMAGE.equals(widgetType)) {
      return "widgets/image";
    }
    if (WIDGET_TYPE.VIDEO.equals(widgetType)) {
      return "widgets/video";
    }
    if (WIDGET_TYPE.MENU.equals(widgetType)) {
      return "widgets/menu";
    }
    return "widget/default";
  }

  String computePageContent(PageDTO page) {
    return page.getName();
  }

  String computePageHeader(PageDTO page) {
    return page.getName() + "_header";
  }

  String computeNewsTemplate(String widgetId) {
    if (StringUtils.hasText(widgetId)) {
      WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId));
      if (widget != null && StringUtils.hasText(widget.getPersonalization())) {
        return "widget_" + widget.getName();
      }
    }
    return "widgets/blog_entry";
  }

  String computePageFooter(PageDTO page) {
    return page.getName() + "_footer";
  }

  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  public PageWrapper<NewsEntryDisplayBean> computePageWrapperOfNews(WidgetDTO widget, Locale locale, int pageNumber) {
    Page<NewsEntryDisplayBean> pagedNewsEntries = computeNewsEntries(locale, pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    return new PageWrapperBuilder<NewsEntryDisplayBean>().currentPageNumber(currentPageNumber).firstPage(isFirstPage)
        .lastPage(isLastPage).page(pagedNewsEntries).totalPages(totalPages).pageBaseUrl("/widgets/" + widget.getName())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  public Page<NewsEntryDisplayBean> computeNewsEntries(Locale locale, int pageNumber) {
    List<NewsEntryDisplayBean> newsEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    pagedNewsEntries.getContent().forEach(newsEntry -> newsEntries.add(computeNewsEntryDisplayBean(locale, newsEntry)));

    return new PageImpl<>(newsEntries, pageRequest, pagedNewsEntries.getTotalElements());
  }

  public NewsEntryDisplayBean computeNewsEntry(Locale locale, String newsEntryId) {

    NewsEntryDTO newsEntryFromDB = newsEntryService.getEntity(Long.valueOf(newsEntryId));
    return computeNewsEntryDisplayBean(locale, newsEntryFromDB);
  }

  public NewsEntryDisplayBean computeNewsEntryDisplayBean(Locale locale, NewsEntryDTO newsEntryDTO) {

    String labelPar = getI18nValue("news.entry.by", locale);
    String labelLe = getI18nValue("news.entry.the", locale);

    return new NewsEntryDisplayBean(newsEntryDTO, contextHolder.getImageDisplaySrc(), labelPar, labelLe,
        contextHolder.getDateFormat());
  }

  private List<NewsEntryDTO> computeNewsEntriesForPage(int pageNumber) {

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);

    return pagedNewsEntries.getContent();

  }

  Map<String, Object> computeWidgetModel(WidgetDTO widget, int pageNumber, Locale locale, String pageName) {

    WIDGET_TYPE widgetType = widget.getType();
    if (WIDGET_TYPE.HTML.equals(widgetType)) {
      return new HashMap<>();
    }
    Map<String, Object> widgetModel = new HashMap<>();
    if (WIDGET_TYPE.BLOG.equals(widgetType)) {
      return computeWidgetModelForBlog(widget, pageNumber, locale);
    }
    if (WIDGET_TYPE.MENU.equals(widgetType)) {
      return computeWidgetModelForMenu(pageName, locale);
    }
    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }
    if (WIDGET_TYPE.CAROUSEL.equals(widgetType)) {
      return computeWidgetModelForCarousel(widget);
    }
    if (WIDGET_TYPE.IMAGE.equals(widgetType)) {
      return computeWidgetModelForImage(widget);
    }
    if (WIDGET_TYPE.VIDEO.equals(widgetType)) {
      return computeWidgetModelForVideo(widget);
    }

    return widgetModel;
  }

  Map<String, Object> computeWidgetModelForBlog(WidgetDTO widget, int pageNumber, Locale locale) {
    Map<String, Object> widgetModel = new HashMap<>();

    PageWrapper<NewsEntryDisplayBean> pagedNewsWrapped = computePageWrapperOfNews(widget, locale, pageNumber);

    List<NewsEntryDTO> entries = computeNewsEntriesForPage(pageNumber);
    List<String> entriesIds = new ArrayList<>();
    entries.forEach(entry -> entriesIds.add(String.valueOf(entry.getId())));

    widgetModel.put("wrappedNews", pagedNewsWrapped);
    widgetModel.put("news", entriesIds);
    widgetModel.put("widgetId", String.valueOf(widget.getId()));
    widgetModel.put("emptyMessage", getI18nValue("actualites.empty", locale));

    return widgetModel;
  }

  Map<String, Object> computeWidgetModelForCarousel(WidgetDTO widget) {

    Map<String, Object> widgetModel = new HashMap<>();
    CarouselDTO carousel = carouselService.getEntity(Long.valueOf(widget.getEntityId()));
    widgetModel.put("carousel", carousel);

    return widgetModel;
  }

  Map<String, Object> computeWidgetModelForImage(WidgetDTO widget) {
    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO image = mediaService.getEntity(Long.parseLong(widget.getEntityId()));
    widgetModel.put("mediaUrl", image.getSrc());

    return widgetModel;

  }

  Map<String, Object> computeWidgetModelForVideo(WidgetDTO widget) {
    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO video = mediaService.getEntity(Long.parseLong(widget.getEntityId()));
    widgetModel.put("mediaUrl", video.getSrc());

    return widgetModel;
  }

  Map<String, Object> computeWidgetModelForMenu(String pageName, Locale locale) {
    Map<String, Object> widgetModel = new HashMap<>();

    widgetModel.put("menuItems", computeMenuItems(pageService.getPageByName(pageName), locale));

    return widgetModel;
  }

}
