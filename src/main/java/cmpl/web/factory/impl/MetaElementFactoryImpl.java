package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.news.dto.NewsContentDTO;
import cmpl.web.model.news.dto.NewsEntryDTO;
import cmpl.web.model.page.PAGE;
import cmpl.web.service.NewsEntryService;

/**
 * Implementation de l'interface pour la factory des meta elements du site pour le SEO
 * 
 * @author Louis
 *
 */
public class MetaElementFactoryImpl extends BaseFactoryImpl implements MetaElementFactory {

  private final NewsEntryService newsEntryService;

  private static final String OG_URL = "og:url";
  private static final String OG_TYPE = "og:type";
  private static final String OG_TITLE = "og:title";
  private static final String OG_DESCRIPTION = "og:description";
  private static final String DESCRIPTION = "description";
  private static final String TITLE = "title";
  private static final String VIEWPORT = "viewport";
  private static final String WEBSITE = "website";
  private static final String LANGUAGE = "language";
  private static final String WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";
  private static final String SLASH = "/";
  private static final String BASE_URL = "http://cm-pl.com";

  private MetaElementFactoryImpl(WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    super(messageSource);
    this.newsEntryService = newsEntryService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param messageSource
   * @param newsEntryService
   * @return
   */
  public static MetaElementFactoryImpl fromMessageSource(WebMessageSourceImpl messageSource,
      NewsEntryService newsEntryService) {
    return new MetaElementFactoryImpl(messageSource, newsEntryService);
  }

  @Override
  public List<MetaElement> computeMetaElementsForPage(Locale locale, PAGE page) {

    List<MetaElement> metaElements = new ArrayList<>();

    metaElements.add(computeTitle(locale, page));
    metaElements.add(computeDescription(locale, page));
    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElement> computeMetaElementsForBackPage(Locale locale) {

    List<MetaElement> metaElements = new ArrayList<>();

    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElement> computeOpenGraphMetaElementsForPage(Locale locale, PAGE page) {
    List<MetaElement> openGraphMetaElements = new ArrayList<>();

    openGraphMetaElements.add(computeOpenGraphTitle(locale, page));
    openGraphMetaElements.add(computeOpenGraphType());
    openGraphMetaElements.add(computeOpenGraphUrl(locale, page));
    openGraphMetaElements.add(computeOpenGraphDescription(locale, page));
    return openGraphMetaElements;
  }

  @Override
  public List<MetaElement> computeMetaElementsForNewsEntry(Locale locale, PAGE page, String newsEntryId) {

    List<MetaElement> metaElements = new ArrayList<>();

    NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    metaElements.add(computeTitleForNewsEntry(newsEntry));
    metaElements.add(computeDescriptionForNewsEntry(locale, page, newsEntry));
    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElement> computeOpenGraphMetaElementsNewsEntry(Locale locale, PAGE page, String newsEntryId) {
    List<MetaElement> openGraphMetaElements = new ArrayList<>();

    NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    openGraphMetaElements.add(computeOpenGraphTitleForNewsEntry(newsEntry));
    openGraphMetaElements.add(computeOpenGraphType());
    openGraphMetaElements.add(computeOpenGraphUrlForNewsEntry(locale, newsEntry));
    openGraphMetaElements.add(computeOpenGraphDescriptionForNewsEntry(locale, page, newsEntry));

    return openGraphMetaElements;
  }

  MetaElement computeTitle(Locale locale, PAGE page) {
    return computeMetaElement(TITLE, getI18nValue(page.getTitle(), locale));
  }

  MetaElement computeDescription(Locale locale, PAGE page) {
    return computeMetaElement(DESCRIPTION, getI18nValue(page.getDescription(), locale));
  }

  MetaElement computeTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeMetaElement(TITLE, newsEntry.getTitle());
  }

  MetaElement computeDescriptionForNewsEntry(Locale locale, PAGE page, NewsEntryDTO newsEntry) {
    NewsContentDTO content = newsEntry.getNewsContent();
    if (content == null || StringUtils.isEmpty(content.getContent())) {
      return computeMetaElement(DESCRIPTION, getI18nValue(page.getDescription(), locale));
    }
    return computeMetaElement(DESCRIPTION, content.getContent());
  }

  MetaElement computeLanguage(Locale locale) {
    return computeMetaElement(LANGUAGE, locale.getLanguage());
  }

  MetaElement computeViewPort() {
    return computeMetaElement(VIEWPORT, WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO);
  }

  MetaElement computeOpenGraphTitle(Locale locale, PAGE page) {
    return computeOpenGraphMetaElement(OG_TITLE, getI18nValue(page.getTitle(), locale));
  }

  MetaElement computeOpenGraphDescription(Locale locale, PAGE page) {
    return computeOpenGraphMetaElement(OG_DESCRIPTION, getI18nValue(page.getDescription(), locale));
  }

  MetaElement computeOpenGraphTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeOpenGraphMetaElement(OG_TITLE, newsEntry.getTitle());
  }

  MetaElement computeOpenGraphDescriptionForNewsEntry(Locale locale, PAGE page, NewsEntryDTO newsEntry) {
    NewsContentDTO content = newsEntry.getNewsContent();
    if (content == null || StringUtils.isEmpty(content.getContent())) {
      return computeMetaElement(OG_DESCRIPTION, getI18nValue(page.getDescription(), locale));
    }
    return computeOpenGraphMetaElement(OG_DESCRIPTION, content.getContent());
  }

  MetaElement computeOpenGraphType() {
    return computeOpenGraphMetaElement(OG_TYPE, WEBSITE);
  }

  MetaElement computeOpenGraphUrl(Locale locale, PAGE page) {
    MENU pageMenu = MENU.getByPage(page);
    return computeOpenGraphMetaElement(OG_URL, BASE_URL + getI18nValue(pageMenu.getHref(), locale));
  }

  MetaElement computeOpenGraphUrlForNewsEntry(Locale locale, NewsEntryDTO newsEntry) {
    MENU pageMenu = MENU.getByPage(PAGE.NEWS);
    return computeOpenGraphMetaElement(OG_URL,
        BASE_URL + getI18nValue(pageMenu.getHref(), locale) + SLASH + +newsEntry.getId());
  }

  MetaElement computeMetaElement(String name, String content) {
    MetaElement metaElement = new MetaElement();
    metaElement.setName(name);
    metaElement.setContent(content);
    return metaElement;

  }

  MetaElement computeOpenGraphMetaElement(String property, String content) {
    MetaElement metaElement = new MetaElement();
    metaElement.setProperty(property);
    metaElement.setContent(content);
    return metaElement;

  }

}
