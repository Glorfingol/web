package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class LasersController {

  private final DisplayFactory displayFactory;

  @Autowired
  LasersController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/lasers-medicaux")
  public ModelAndView printAppointments() {

    return displayFactory.computeModelAndViewForPage(PAGE.MEDICAL_LASERS, "fr");
  }

}
