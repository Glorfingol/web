package cmpl.web.facebook;

import java.util.Locale;

import cmpl.web.core.model.BaseException;

/**
 * Dispatcher pour le controller facebook
 * 
 * @author Louis
 *
 */
public interface FacebookDispatcher {

  /**
   * Permet la creation d'une entite a partir d'un post facebook
   * 
   * @param facebookImportRequest
   * @param locale
   * @return
   * @throws BaseException
   */
  FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale) throws BaseException;

}
