package cmpl.web.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import cmpl.web.model.page.BACK_PAGE;

/**
 * Interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public interface NewsManagerDisplayFactory {

  /**
   * Creer le model and view pour l'edition d'une NewsEntry
   * 
   * @param backPage
   * @param locale
   * @param newsEntryId
   * @return
   */
  ModelAndView computeModelAndViewForOneNewsEntry(BACK_PAGE backPage, Locale locale, String newsEntryId);

  /**
   * Creer le model and view pour l'affichage de toutes les NewsEntry modifiables
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale);

}
