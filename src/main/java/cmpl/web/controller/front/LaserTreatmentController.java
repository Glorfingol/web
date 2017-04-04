package cmpl.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class LaserTreatmentController {

  private final DisplayFactory displayFactory;

  @Autowired
  LaserTreatmentController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/traitement_laser")
  public ModelAndView printLaserTreatment() {

    return displayFactory.computeModelAndViewForPage(PAGE.LASER_TREATMENT, "fr");
  }

}
