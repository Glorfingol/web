package cmpl.web.page;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface PagesManagerDisplayFactory {

  /**
   * Creer le model and view pour l'affichage de toutes les NewsEntry modifiables
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForViewAllPages(BACK_PAGE backPage, Locale locale, int pageNumber);

  /**
   * Creer le model and view pour l'edition d'une page
   * 
   * @param backPage
   * @param locale
   * @param newsEntryId
   * @return
   */
  ModelAndView computeModelAndViewForUpdatePage(BACK_PAGE backPage, Locale locale, String pageId);

  /**
   * Creer le model and view pour l'affichage de la creation d'une page
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForCreatePage(BACK_PAGE backPage, Locale locale);
}
