package cmpl.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SoonController {

  @GetMapping(value = "/soon")
  public ModelAndView printPrices() {

    return new ModelAndView("pages/soon");
  }

}
