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
 * Controller pour les techniques de soin
 * 
 * @author Louis
 *
 */
@Controller
public class TechnicsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TechnicsController.class);

  private final DisplayFactory displayFactory;

  @Autowired
  TechnicsController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour les techniques de soin
   * 
   * @return
   */
  @GetMapping(value = "/techniques")
  public ModelAndView printTechnicsTreatment() {

    LOGGER.info("Accès à la page " + PAGES.TECHNICS.name());
    return displayFactory.computeModelAndViewForPage(PAGES.TECHNICS, Locale.FRANCE);
  }

}