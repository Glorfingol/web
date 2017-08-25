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

    LOGGER.info("Accès à la page " + PAGES.PRICES.name());
    return displayFactory.computeModelAndViewForPage(PAGES.PRICES, Locale.FRANCE);
  }

}
