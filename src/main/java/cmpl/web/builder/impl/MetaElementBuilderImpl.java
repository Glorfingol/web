package cmpl.web.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.AbstractBuilder;
import cmpl.web.builder.MetaElementBuilder;
import cmpl.web.model.meta.MetaElement;
import cmpl.web.model.page.PAGE;

public class MetaElementBuilderImpl extends AbstractBuilder implements MetaElementBuilder {

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  public static MetaElementBuilderImpl fromResourceBundleMessageSource(
      ResourceBundleMessageSource resourceBundleMessageSource) {
    return new MetaElementBuilderImpl(resourceBundleMessageSource);
  }

  private MetaElementBuilderImpl(ResourceBundleMessageSource resourceBundleMessageSource) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
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

  MetaElement computeTitle(Locale locale, PAGE page) {

    MetaElement title = new MetaElement();
    title.setName("title");
    title.setContent(getI18nValue(page.getTitle(), locale));

    return title;
  }

  MetaElement computeDescription(Locale locale, PAGE page) {

    MetaElement description = new MetaElement();
    description.setName("description");
    description.setContent(getI18nValue(page.getDescription(), locale));

    return description;

  }

  MetaElement computeLanguage(Locale locale) {

    MetaElement language = new MetaElement();
    language.setName("language");
    language.setContent(locale.getLanguage());

    return language;

  }

  MetaElement computeViewPort() {

    MetaElement viewPort = new MetaElement();
    viewPort.setName("viewport");
    viewPort.setContent("width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no");

    return viewPort;

  }

  @Override
  protected String getI18nValue(String key, Locale locale) {
    return resourceBundleMessageSource.getMessage(key, null, locale);
  }

}
