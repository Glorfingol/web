package cmpl.web.controller.back;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.LoginDisplayFactory;
import cmpl.web.model.page.BACK_PAGE;

@Controller
public class LoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private final LoginDisplayFactory displayFactory;

  @Autowired
  public LoginController(LoginDisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView printLogin() {

    LOGGER.info("Accès à la page " + BACK_PAGE.LOGIN.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, Locale.FRANCE);
  }

}
