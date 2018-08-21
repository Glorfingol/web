package com.cmpl.web.core.common.message;

import java.util.Locale;

/**
 * Interface de cles i18n
 *
 * @author Louis
 */
public interface WebMessageSource {

  /**
   * Recupere une valeur i18n en fonction de sa cle
   */
  String getI18n(String code, Locale locale);

  /**
   * Recupere une valeur i18n en fonction de sa cle et de parametres
   */
  String getI18n(String code, Locale locale, Object... args);

  String getMessage(String code, Locale locale, Object... args);

}
