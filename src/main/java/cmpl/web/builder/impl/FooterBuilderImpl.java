package cmpl.web.builder.impl;

import java.util.Locale;

import cmpl.web.builder.AbstractBuilder;
import cmpl.web.builder.FooterBuilder;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.footer.Footer;

public class FooterBuilderImpl extends AbstractBuilder implements FooterBuilder {

  private final WebMessageSourceImpl messageSource;

  private FooterBuilderImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  public static FooterBuilderImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new FooterBuilderImpl(messageSource);
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
    return messageSource.getI18n(key, locale);
  }
}
