package cmpl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class MedicalCareController {

  private final DisplayFactory displayFactory;

  @Autowired
  MedicalCareController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/soins-medicaux")
  public ModelAndView printAppointments() {
    return displayFactory.computeModelAndViewForPage(PAGE.MEDICAL_CARE, "fr");
  }

}
