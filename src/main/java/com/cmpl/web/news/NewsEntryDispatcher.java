package com.cmpl.web.news;

import java.util.Locale;

import com.cmpl.web.core.model.BaseException;

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

  void saveNewsTemplate(String content) throws BaseException;

}
