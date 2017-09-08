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
 * Controller pour la page Gynecologie
 * 
 * @author Louis
 *
 */
@Controller
public class GynecologistController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GynecologistController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  GynecologistController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page gynecologie
   * 
   * @return
   */
  @GetMapping(value = "/gynecologue")
  public ModelAndView printGenitalAesthetic() {

    LOGGER.info("Accès à la page " + PAGES.GYNECOLOGIST.name());
    return displayFactory.computeModelAndViewForPage(PAGES.GYNECOLOGIST, Locale.FRANCE);
  }

}