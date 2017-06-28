package cmpl.web.factory;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.model.page.PAGE;

/**
 * Interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public interface MenuFactory {

  /**
   * Creer le menu front pour la page du front
   * 
   * @param page
   * @param locale
   * @return
   */
  List<MenuItem> computeMenuItems(PAGE page, Locale locale);

  /**
   * Creer le menu back pour la page du back
   * 
   * @param backPage
   * @param locale
   * @return
   */
  List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale);

}
