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
public class GynecologistController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GynecologistController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  GynecologistController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/gynecologue")
  public ModelAndView printGenitalAesthetic() {

    LOGGER.info("Accès à la page " + PAGE.GYNECOLOGIST.name());
    return displayFactory.computeModelAndViewForPage(PAGE.GYNECOLOGIST, Locale.FRANCE);
  }

}
