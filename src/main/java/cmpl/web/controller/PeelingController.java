package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class PeelingController {

  private final DisplayFactory displayFactory;

  @Autowired
  PeelingController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/peeling")
  public ModelAndView printPeeling() {
    return displayFactory.computeModelAndViewForPage(PAGE.PEELING, "fr");
  }

}
