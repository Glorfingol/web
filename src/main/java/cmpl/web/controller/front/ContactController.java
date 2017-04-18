package cmpl.web.controller.front;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.DisplayFactory;
import cmpl.web.model.page.PAGE;

@Controller
public class ContactController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  ContactController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/contact")
  public ModelAndView printLaserHairRemoval() {

    LOGGER.info("Accès à la page " + PAGE.CONTACT.name());
    return displayFactory.computeModelAndViewForPage(PAGE.CONTACT, Locale.FRANCE);
  }

}
