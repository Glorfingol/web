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
 * Controller pour la page des rendez-vous
 * 
 * @author Louis
 *
 */
@Controller
public class AppointmentController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);
  private final DisplayFactory displayFactory;

  @Autowired
  AppointmentController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page des rendez-vous
   * 
   * @return
   */
  @GetMapping(value = "/rendez-vous")
  public ModelAndView printAppointments() {

    LOGGER.info("Accès à la page " + PAGES.APPOINTMENT.name());
    return displayFactory.computeModelAndViewForPage(PAGES.APPOINTMENT, Locale.FRANCE);
  }

}
