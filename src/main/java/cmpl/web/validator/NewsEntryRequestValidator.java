package cmpl.web.validator;

import java.util.Locale;

import cmpl.web.model.news.error.Error;
import cmpl.web.model.news.rest.news.NewsEntryRequest;

/**
 * Interface de validation des modifications de NewsEntry
 * 
 * @author Louis
 *
 */
public interface NewsEntryRequestValidator {

  /**
   * Verifie que la requete de creation de NewsEntry est valide
   * 
   * @param request
   * @param locale
   * @return
   */
  Error validateCreate(NewsEntryRequest request, Locale locale);

  /**
   * Verifie que la requete de modification de NewsEntry est valide
   * 
   * @param request
   * @param newsEntryId
   * @param locale
   * @return
   */
  Error validateUpdate(NewsEntryRequest request, String newsEntryId, Locale locale);

  /**
   * Verifie que la requete de suppression de NewsEntry est valide
   * 
   * @param newsEntryId
   * @param locale
   * @return
   */
  Error validateDelete(String newsEntryId, Locale locale);

  /**
   * Verifie que la requete de recuperation d'une NewsEntry est valide
   * 
   * @param newsEntryId
   * @param locale
   * @return
   */
  Error validateGet(String newsEntryId, Locale locale);

}
