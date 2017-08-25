package cmpl.web.news;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.PAGES;

/**
 * Interface pour la factory des pages d'actualite sur le front
 * 
 * @author Louis
 *
 */
public interface NewsDisplayFactory {

  /**
   * Creer le model and view de toutes les actualites
   * 
   * @param page
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForPage(PAGES page, Locale locale);

  /**
   * Creer le model and view pour une page d'actualites
   * 
   * @param page
   * @param locale
   * @param pageNumber
   * @return
   */
  ModelAndView computeModelAndViewForPage(PAGES page, Locale locale, int pageNumber);

  /**
   * Creer le model and view pour une actualite
   * 
   * @param locale
   * @param newsEntryId
   * @return
   */
  ModelAndView computeModelAndViewForNewsEntry(Locale locale, String newsEntryId);

}
