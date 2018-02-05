package com.cmpl.web.manager.ui.core.login;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;

/**
 * Controller pour afficher la page de login
 * 
 * @author Louis
 *
 */
@Controller
public class LoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private final LoginDisplayFactory displayFactory;

  /**
   * Constructeur en autowired
   * 
   * @param displayFactory
   */
  public LoginController(LoginDisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page de login
   * 
   * @return
   */
  @GetMapping(value = "/login")
  public ModelAndView printLogin() {

    LOGGER.info("Accès à la page " + BACK_PAGE.LOGIN.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, Locale.FRANCE);
  }

}