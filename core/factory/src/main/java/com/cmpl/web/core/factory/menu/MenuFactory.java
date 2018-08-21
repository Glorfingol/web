package com.cmpl.web.core.factory.menu;

import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageDTO;
import java.util.List;
import java.util.Locale;

/**
 * Interface pour la factory du menu
 *
 * @author Louis
 */
public interface MenuFactory {

  /**
   * Creer le menu front pour la page du front
   */
  List<MenuItem> computeMenuItems(PageDTO page, Locale locale);

  /**
   * Creer le menu back pour la page du back
   */
  List<MenuItem> computeBackMenuItems(BackPage backPage, Locale locale);

}
