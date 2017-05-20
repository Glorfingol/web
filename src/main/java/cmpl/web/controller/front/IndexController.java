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
public class IndexController {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  IndexController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping(value = "/")
  public ModelAndView printIndex() {

    LOGGER.info("Accès à la page " + PAGE.INDEX.name());
    return displayFactory.computeModelAndViewForPage(PAGE.INDEX, Locale.FRANCE);
  }

}
