package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.PAGE;

/**
 * Interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public interface DisplayFactory {

  /**
   * Creer le model and view correpsondant a la page front du site
   * 
   * @param page
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForPage(PAGE page, Locale locale);

}
