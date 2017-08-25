package cmpl.web.page;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.factory.DisplayFactory;

/**
 * Controller pour afficher les pages du front office
 * 
 * @author Louis
 *
 */
@Controller
public class PageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  PageController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour les pages
   * 
   * @param pageName
   * @return
   */
  @GetMapping(value = "/pages/{pageName}")
  public ModelAndView printPage(@PathVariable(value = "pageName") String pageName) {

    LOGGER.info("Accès à la page " + pageName);
    return displayFactory.computeModelAndViewForPage(pageName, Locale.FRANCE);
  }

}
