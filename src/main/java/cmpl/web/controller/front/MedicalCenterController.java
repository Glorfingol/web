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

@Controller
public class MedicalCenterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MedicalCenterController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  MedicalCenterController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping(value = "/centre-medical")
  public ModelAndView printMedicalCenter() {

    LOGGER.info("Accès à la page " + PAGE.CENTER.name());
    return displayFactory.computeModelAndViewForPage(PAGE.CENTER, Locale.FRANCE);
  }

}
