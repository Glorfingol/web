package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class FacialInjectionsController {

  private final DisplayFactory displayFactory;

  @Autowired
  FacialInjectionsController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/injections_visage")
  public ModelAndView printFacialInjections() {
    return displayFactory.computeModelAndViewForPage(PAGE.FACIAL_INJECTION, "fr");
  }

}
