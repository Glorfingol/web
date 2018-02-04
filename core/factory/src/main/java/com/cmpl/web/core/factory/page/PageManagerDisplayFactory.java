package com.cmpl.web.core.factory.page;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface PageManagerDisplayFactory {

  /**
   * Creer le model and view pour l'affichage de toutes les NewsEntry modifiables
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForViewAllPages(Locale locale, int pageNumber);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePage(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageHeader(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageFooter(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param pageId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePageOpenGraphMeta(Locale locale, String pageId);

  /**
   * Creer le model and view pour l'affichage de la creation d'une page
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForCreatePage(Locale locale);

  ModelAndView computeModelAndViewForUpdatePageWidgets(Locale locale, String pageId);
}
