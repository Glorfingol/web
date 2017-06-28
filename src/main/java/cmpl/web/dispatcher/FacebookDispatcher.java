package cmpl.web.dispatcher;

import java.util.Locale;

import cmpl.web.model.BaseException;
import cmpl.web.model.facebook.FacebookImportRequest;
import cmpl.web.model.facebook.FacebookImportResponse;

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
