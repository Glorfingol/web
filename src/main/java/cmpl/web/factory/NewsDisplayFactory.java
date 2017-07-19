package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.PAGE;

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
  ModelAndView computeModelAndViewForPage(PAGE page, Locale locale);

  /**
   * Creer le model and view pour une page d'actualites
   * 
   * @param page
   * @param locale
   * @param pageNumber
   * @return
   */
  ModelAndView computeModelAndViewForPage(PAGE page, Locale locale, int pageNumber);

  /**
   * Creer le model and view pour une actualite
   * 
   * @param locale
   * @param newsEntryId
   * @return
   */
  ModelAndView computeModelAndViewForNewsEntry(Locale locale, String newsEntryId);

}
