package cmpl.web.factory.impl;

import java.util.Locale;

import cmpl.web.factory.BaseFactory;
import cmpl.web.message.WebMessageSource;

/**
 * Implmentaiton de l'interface commune aux factory utilisant des cles i18n
 * 
 * @author Louis
 *
 */
public class BaseFactoryImpl implements BaseFactory {

  protected WebMessageSource messageSource;

  protected BaseFactoryImpl(WebMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String getI18nValue(String key, Locale locale) {
    return messageSource.getI18n(key, locale);
  }

}
