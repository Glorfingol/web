package cmpl.web.dispatcher;

import java.util.Locale;

import cmpl.web.model.BaseException;
import cmpl.web.model.news.rest.news.NewsEntryRequest;
import cmpl.web.model.news.rest.news.NewsEntryResponse;

/**
 * Dispatcher pour le controller des NewsEntry
 * 
 * @author Louis
 *
 */
public interface NewsEntryDispatcher {

  /**
   * Permet la creation d'une NewsEntry a partir d'une requete
   * 
   * @param newsEntryRequest
   * @param locale
   * @return
   * @throws BaseException
   */
  NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale) throws BaseException;

  /**
   * Permet la modificaiton d'une NewsEntry a partir d'une requete
   * 
   * @param newsEntryRequest
   * @param newsEntryId
   * @param locale
   * @return
   * @throws BaseException
   */
  NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, Locale locale)
      throws BaseException;

  /**
   * Permet le suppression d'une NewsEntry a partir d'une requete
   * 
   * @param newsEntryId
   * @param locale
   * @throws BaseException
   */
  void deleteEntity(String newsEntryId, Locale locale) throws BaseException;

}
