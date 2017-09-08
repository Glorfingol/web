package cmpl.web.page;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.factory.DisplayFactory;

/**
 * Controller pour les horaires d'ouverture
 * 
 * @author Louis
 *
 */
@Controller
public class OpeningHoursController {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpeningHoursController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  OpeningHoursController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour les horaires d'ouverture
   * 
   * @return
   */
  @GetMapping(value = "/horaires")
  public ModelAndView printOpeningHours() {

    LOGGER.info("Accès à la page " + PAGES.OPENING_HOURS.name());
    return displayFactory.computeModelAndViewForPage(PAGES.OPENING_HOURS, Locale.FRANCE);
  }

}