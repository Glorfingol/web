package cmpl.web.builder.impl;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.AbstractBuilder;
import cmpl.web.builder.FooterBuilder;
import cmpl.web.model.footer.Footer;

public class FooterBuilderImpl extends AbstractBuilder implements FooterBuilder {

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  public static FooterBuilderImpl fromResourceBundleMessageSource(
      ResourceBundleMessageSource resourceBundleMessageSource) {
    return new FooterBuilderImpl(resourceBundleMessageSource);
  }

  private FooterBuilderImpl(ResourceBundleMessageSource resourceBundleMessageSource) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
  }

  @Override
  public Footer computeFooter(Locale locale) {

    Footer footer = new Footer();

    footer.setAdresse(getI18nValue("footer.address", locale));
    footer.setLibelle(getI18nValue("footer.label", locale));
    footer.setTelephone(getI18nValue("footer.phone", locale));

    return footer;
  }

  @Override
  protected String getI18nValue(String key, Locale locale) {
    return resourceBundleMessageSource.getMessage(key, null, locale);
  }
}
