package com.cmpl.web.facebook;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

/**
 * Dispatcher pour le controller facebook
 *
 * @author Louis
 */
public interface FacebookDispatcher {

  /**
   * Permet la creation d'une entite a partir d'un post facebook
   */
  FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale)
      throws BaseException;

}
