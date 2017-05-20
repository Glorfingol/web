package cmpl.web.controller.back;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.factory.FacebookDisplayFactory;
import cmpl.web.model.page.BACK_PAGE;

@Controller
public class FacebookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookController.class);

  private final FacebookDisplayFactory facebookDisplayFactory;

  @Autowired
  public FacebookController(FacebookDisplayFactory facebookDisplayFactory) {
    this.facebookDisplayFactory = facebookDisplayFactory;
  }

  @GetMapping(value = "/manager/facebook")
  public ModelAndView printFacebookAccess() {
    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_ACCESS.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);
  }

  @GetMapping(value = "/manager/facebook/import")
  public ModelAndView printFacebookImport() {

    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_IMPORT.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookImportPage(Locale.FRANCE);
  }

}
