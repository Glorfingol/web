package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class TechnicsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TechnicsController.class);

  private final DisplayFactory displayFactory;

  @Autowired
  TechnicsController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/techniques")
  public ModelAndView printTechnicsTreatment() {

    LOGGER.info("Accès à la page " + PAGE.TECHNICS.name());
    return displayFactory.computeModelAndViewForPage(PAGE.TECHNICS, Locale.FRANCE);
  }

}
