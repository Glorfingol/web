package cmpl.web.core.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.factory.DisplayFactory;

/**
 * Controller pour la page d'accueil
 * 
 * @author Louis
 *
 */
@Controller
public class IndexController {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  IndexController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page d'accueil
   * 
   * @return
   */
  @GetMapping(value = "/")
  public ModelAndView printIndex() {

    LOGGER.info("Accès à la page d'index");
    return displayFactory.computeModelAndViewForPage("accueil", Locale.FRANCE, 0);
  }

}
