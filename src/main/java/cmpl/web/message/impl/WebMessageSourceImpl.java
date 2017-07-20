package cmpl.web.message.impl;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.message.WebMessageSource;

/**
 * Implementation de l'interface de i18n
 * 
 * @author Louis
 *
 */
public class WebMessageSourceImpl extends ResourceBundleMessageSource implements WebMessageSource {

  @Override
  public String getI18n(String code, Locale locale) {
    return getMessage(code, null, locale);
  }

  @Override
  public String getI18n(String code, Locale locale, Object... args) {
    return getMessage(code, args, locale);
  }

}
