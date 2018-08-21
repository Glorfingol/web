package com.cmpl.web.core.factory;

import com.cmpl.web.core.page.BackPage;
import java.util.Locale;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface commune de factory pour le back
 *
 * @author Louis
 */
public interface BackDisplayFactory {

  /**
   * Calcule le model and view commun pour les pages du back
   */
  ModelAndView computeModelAndViewForBackPage(BackPage backPage, Locale locale);


}
