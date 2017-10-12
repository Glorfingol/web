package cmpl.web.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.core.factory.BaseFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.news.NewsEntryDTO;

/**
 * Implementation de l'interface pour la factory des meta elements du site pour le SEO
 * 
 * @author Louis
 *
 */
public class MetaElementFactoryImpl extends BaseFactoryImpl implements MetaElementFactory {

  private static final String OG_TYPE = "og:type";
  private static final String OG_TITLE = "og:title";
  private static final String TITLE = "title";
  private static final String VIEWPORT = "viewport";
  private static final String WEBSITE = "website";
  private static final String LANGUAGE = "language";
  private static final String WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO = "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no";

  public MetaElementFactoryImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public List<MetaElementToDelete> computeMetaElementsForBackPage(Locale locale) {

    List<MetaElementToDelete> metaElements = new ArrayList<>();

    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  MetaElementToDelete computeTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeMetaElement(TITLE, newsEntry.getTitle());
  }

  MetaElementToDelete computeLanguage(Locale locale) {
    return computeMetaElement(LANGUAGE, locale.getLanguage());
  }

  MetaElementToDelete computeViewPort() {
    return computeMetaElement(VIEWPORT, WIDTH_DEVICE_WIDTH_INITIAL_SCALE_1_MAXIMUM_SCALE_1_USER_SCALABLE_NO);
  }

  MetaElementToDelete computeOpenGraphTitleForNewsEntry(NewsEntryDTO newsEntry) {
    return computeOpenGraphMetaElement(OG_TITLE, newsEntry.getTitle());
  }

  MetaElementToDelete computeOpenGraphType() {
    return computeOpenGraphMetaElement(OG_TYPE, WEBSITE);
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
