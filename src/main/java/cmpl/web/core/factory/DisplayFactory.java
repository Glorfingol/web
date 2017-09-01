package cmpl.web.core.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.PAGES;

/**
 * Interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public interface DisplayFactory {

  /**
   * Creer le model and view correspondant a la page front du site
   * 
   * @param page
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForPage(PAGES page, Locale locale);

  /**
   * Creer le model and view correspondant a la page front du site
   * 
   * @param pageName
   * @param locale
   * @param pageNumber
   * @return
   */
  ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber);

  /**
   * Creer le model and view correspondant a la page front du site pour une entite
   * 
   * @param pageName
   * @param locale
   * @param entity
   * @return
   */
  ModelAndView computeModelAndViewForPage(String pageName, Locale locale, String entity);

}
