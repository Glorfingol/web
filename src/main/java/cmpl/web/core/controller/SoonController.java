package cmpl.web.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pour la page d'attente
 * 
 * @author Louis
 *
 */
@Controller
public class SoonController {

  /**
   * Mapping pour la page d'attente
   * 
   * @return
   */
  @GetMapping(value = "/soon")
  public ModelAndView printPrices() {

    ModelAndView soon = new ModelAndView("pages/soon");
    soon.addObject("facebookLink", "https://www.facebook.com/cm.paul.langevin/");

    return soon;
  }

}
