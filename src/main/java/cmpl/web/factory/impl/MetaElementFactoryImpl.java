package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.factory.MetaElementFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

public class MetaElementFactoryImpl extends BaseFactoryImpl implements MetaElementFactory {

  private MetaElementFactoryImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  public static MetaElementFactoryImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new MetaElementFactoryImpl(messageSource);
  }

  @Override
  public List<MetaElement> computeMetaElementsForPage(Locale locale, PAGE page) {

    List<MetaElement> metaElements = new ArrayList<MetaElement>();

    metaElements.add(computeTitle(locale, page));
    metaElements.add(computeDescription(locale, page));
    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  @Override
  public List<MetaElement> computeMetaElementsForBackPage(Locale locale) {

    List<MetaElement> metaElements = new ArrayList<MetaElement>();

    metaElements.add(computeLanguage(locale));
    metaElements.add(computeViewPort());

    return metaElements;
  }

  MetaElement computeTitle(Locale locale, PAGE page) {
    return computeMetaElement("title", getI18nValue(page.getTitle(), locale));
  }

  MetaElement computeDescription(Locale locale, PAGE page) {
    return computeMetaElement("description", getI18nValue(page.getDescription(), locale));

  }

  MetaElement computeLanguage(Locale locale) {
    return computeMetaElement("language", locale.getLanguage());
  }

  MetaElement computeViewPort() {
    return computeMetaElement("viewport", "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no");
  }

  MetaElement computeMetaElement(String name, String content) {
    MetaElement metaElement = new MetaElement();
    metaElement.setName(name);
    metaElement.setContent(content);
    return metaElement;

  }

}
