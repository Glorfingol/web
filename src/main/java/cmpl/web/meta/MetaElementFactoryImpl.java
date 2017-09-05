package cmpl.web.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import cmpl.web.core.factory.BaseFactoryImpl;
import cmpl.web.menu.MENUS;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsContentDTO;
import cmpl.web.news.NewsEntryDTO;
import cmpl.web.news.NewsEntryService;
import cmpl.web.page.PAGES;

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

  public MetaElementFactoryImpl(WebMessageSourceImpl messageSource, NewsEntryService newsEntryService) {
    super(messageSource);
    this.newsEntryService = newsEntryService;
  }

  @Override
  public List<MetaElementToDelete> computeMetaElementsForPage(Locale locale, PAGES page) {

    List<MetaElementToDelete> metaElements = new ArrayList<>();

    metaElements.add(computeTitle(locale, page));
    metaElements.add(computeDescription(locale, page));
    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElementToDelete> computeMetaElementsForBackPage(Locale locale) {

    List<MetaElementToDelete> metaElements = new ArrayList<>();

    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElementToDelete> computeOpenGraphMetaElementsForPage(Locale locale, PAGES page) {
    List<MetaElementToDelete> openGraphMetaElements = new ArrayList<>();

    openGraphMetaElements.add(computeOpenGraphTitle(locale, page));
    openGraphMetaElements.add(computeOpenGraphType());
    openGraphMetaElements.add(computeOpenGraphUrl(locale, page));
    openGraphMetaElements.add(computeOpenGraphDescription(locale, page));
    return openGraphMetaElements;
  }

  @Override
  public List<MetaElementToDelete> computeMetaElementsForNewsEntry(Locale locale, PAGES page, String newsEntryId) {

    List<MetaElementToDelete> metaElements = new ArrayList<>();

    NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    metaElements.add(computeTitleForNewsEntry(newsEntry));
    metaElements.add(computeDescriptionForNewsEntry(locale, page, newsEntry));
    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElementToDelete> computeOpenGraphMetaElementsNewsEntry(Locale locale, PAGES page, String newsEntryId) {
    List<MetaElementToDelete> openGraphMetaElements = new ArrayList<>();

    NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    openGraphMetaElements.add(computeOpenGraphTitleForNewsEntry(newsEntry));
    openGraphMetaElements.add(computeOpenGraphType());
    openGraphMetaElements.add(computeOpenGraphUrlForNewsEntry(locale, newsEntry));
    openGraphMetaElements.add(computeOpenGraphDescriptionForNewsEntry(locale, page, newsEntry));

    return openGraphMetaElements;
  }

  MetaElementToDelete computeTitle(Locale locale, PAGES page) {
    return computeMetaElement(TITLE, getI18nValue(page.getTitle(), locale));
  }

  MetaElementToDelete computeDescription(Locale locale, PAGES page) {
    return computeMetaElement(DESCRIPTION, getI18nValue(page.getDescription(), locale));
  }

  MetaElementToDelete computeTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeMetaElement(TITLE, newsEntry.getTitle());
  }

  MetaElementToDelete computeDescriptionForNewsEntry(Locale locale, PAGES page, NewsEntryDTO newsEntry) {
    NewsContentDTO content = newsEntry.getNewsContent();
    if (content == null || !StringUtils.hasText(content.getContent())) {
      return computeMetaElement(DESCRIPTION, getI18nValue(page.getDescription(), locale));
    }
    return computeMetaElement(DESCRIPTION, content.getContent());
  }

  MetaElementToDelete computeLanguage(Locale locale) {
    return computeMetaElement(LANGUAGE, locale.getLanguage());
  }

  MetaElementToDelete computeViewPort() {
    return computeMetaElement(VIEWPORT, WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO);
  }

  MetaElementToDelete computeOpenGraphTitle(Locale locale, PAGES page) {
    return computeOpenGraphMetaElement(OG_TITLE, getI18nValue(page.getTitle(), locale));
  }

  MetaElementToDelete computeOpenGraphDescription(Locale locale, PAGES page) {
    return computeOpenGraphMetaElement(OG_DESCRIPTION, getI18nValue(page.getDescription(), locale));
  }

  MetaElementToDelete computeOpenGraphTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeOpenGraphMetaElement(OG_TITLE, newsEntry.getTitle());
  }

  MetaElementToDelete computeOpenGraphDescriptionForNewsEntry(Locale locale, PAGES page, NewsEntryDTO newsEntry) {
    NewsContentDTO content = newsEntry.getNewsContent();
    if (content == null || !StringUtils.hasText(content.getContent())) {
      return computeOpenGraphMetaElement(OG_DESCRIPTION, getI18nValue(page.getDescription(), locale));
    }
    return computeOpenGraphMetaElement(OG_DESCRIPTION, content.getContent());
  }

  MetaElementToDelete computeOpenGraphType() {
    return computeOpenGraphMetaElement(OG_TYPE, WEBSITE);
  }

  MetaElementToDelete computeOpenGraphUrl(Locale locale, PAGES page) {
    MENUS pageMenu = MENUS.getByPageTitle(page);
    return computeOpenGraphMetaElement(OG_URL, BASE_URL + getI18nValue(pageMenu.getHref(), locale));
  }

  MetaElementToDelete computeOpenGraphUrlForNewsEntry(Locale locale, NewsEntryDTO newsEntry) {
    MENUS pageMenu = MENUS.getByPageTitle(PAGES.NEWS);
    return computeOpenGraphMetaElement(OG_URL,
        BASE_URL + getI18nValue(pageMenu.getHref(), locale) + SLASH + +newsEntry.getId());
  }

  MetaElementToDelete computeMetaElement(String name, String content) {
    MetaElementToDelete metaElement = new MetaElementToDelete();
    metaElement.setName(name);
    metaElement.setContent(content);
    return metaElement;

  }

  MetaElementToDelete computeOpenGraphMetaElement(String property, String content) {
    MetaElementToDelete metaElement = new MetaElementToDelete();
    metaElement.setProperty(property);
    metaElement.setContent(content);
    return metaElement;

  }

}
