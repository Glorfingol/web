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
 * Controller pour la page du centre medical
 * 
 * @author Louis
 *
 */
@Controller
public class MedicalCenterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MedicalCenterController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  MedicalCenterController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page du centre medical
   * 
   * @return
   */
  @GetMapping(value = "/centre-medical")
  public ModelAndView printMedicalCenter() {

    LOGGER.info("Accès à la page " + PAGES.CENTER.name());
    return displayFactory.computeModelAndViewForPage(PAGES.CENTER, Locale.FRANCE);
  }

}