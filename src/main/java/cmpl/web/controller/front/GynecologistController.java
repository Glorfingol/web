package cmpl.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class GynecologistController {

  private final DisplayFactory displayFactory;

  @Autowired
  GynecologistController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/gynecologue")
  public ModelAndView printGenitalAesthetic() {
    return displayFactory.computeModelAndViewForPage(PAGE.GYNECOLOGIST, "fr");
  }

}
