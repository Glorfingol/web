package cmpl.web.page;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.core.factory.DisplayFactory;

/**
 * Controller pour la page de contact
 * 
 * @author Louis
 *
 */
@Controller
public class ContactController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  ContactController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page de contact
   * 
   * @return
   */
  @GetMapping(value = "/contact")
  public ModelAndView printContact() {

    LOGGER.info("Accès à la page " + PAGES.CONTACT.name());
    return displayFactory.computeModelAndViewForPage(PAGES.CONTACT, Locale.FRANCE);
  }

}
