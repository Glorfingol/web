package cmpl.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class OpeningHoursController {

  private final DisplayFactory displayFactory;

  @Autowired
  OpeningHoursController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/horaires")
  public ModelAndView printOpeningHours() {
    return displayFactory.computeModelAndViewForPage(PAGE.OPENING_HOURS, "fr");
  }

}
