package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class GenitalAestheticController {

  private final DisplayFactory displayFactory;

  @Autowired
  GenitalAestheticController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/medecine_esthetique_genitale")
  public ModelAndView printGenitalAesthetic() {
    return displayFactory.computeModelAndViewForPage(PAGE.GENITAL_AESTHETIC, "fr");
  }

}
