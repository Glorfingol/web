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
 * Controller pour la page des soins
 * 
 * @author Louis
 *
 */
@Controller
public class MedicalCareController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MedicalCareController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  MedicalCareController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page des soins
   * 
   * @return
   */
  @GetMapping(value = "/soins_medicaux")
  public ModelAndView printFacialInjections() {

    LOGGER.info("Accès à la page " + PAGES.MEDICAL_CARE.name());
    return displayFactory.computeModelAndViewForPage(PAGES.MEDICAL_CARE, Locale.FRANCE);
  }

}
