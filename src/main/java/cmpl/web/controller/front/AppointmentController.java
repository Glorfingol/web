package cmpl.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class AppointmentController {

  private final DisplayFactory displayFactory;

  @Autowired
  AppointmentController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/rendez-vous")
  public ModelAndView printAppointments() {

    return displayFactory.computeModelAndViewForPage(PAGE.APPOINTMENT, "fr");
  }

}
