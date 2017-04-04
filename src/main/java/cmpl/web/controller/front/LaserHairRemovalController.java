package cmpl.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class LaserHairRemovalController {

  private final DisplayFactory displayFactory;

  @Autowired
  LaserHairRemovalController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/epilation_laser")
  public ModelAndView printLaserHairRemoval() {
    return displayFactory.computeModelAndViewForPage(PAGE.LASER_HAIR_REMOVAL, "fr");
  }

}
