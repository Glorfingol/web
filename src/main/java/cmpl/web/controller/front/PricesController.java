package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

/**
 * Controller pour les tarifs
 * 
 * @author Louis
 *
 */
@Controller
public class PricesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PricesController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  PricesController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour les tarifs
   * 
   * @return
   */
  @GetMapping(value = "/tarifs")
  public ModelAndView printPrices() {

    LOGGER.info("Accès à la page " + PAGE.PRICES.name());
    return displayFactory.computeModelAndViewForPage(PAGE.PRICES, Locale.FRANCE);
  }

}
