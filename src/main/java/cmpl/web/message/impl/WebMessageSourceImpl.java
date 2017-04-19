package cmpl.web.message.impl;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.message.WebMessageSource;

public class WebMessageSourceImpl extends ResourceBundleMessageSource implements WebMessageSource {

  @Override
  public String getI18n(String code, Locale locale) {
    return getMessage(code, null, locale);
  }

}
