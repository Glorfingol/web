package cmpl.web.controller.back;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value = "/manager/facebook", method = RequestMethod.GET)
  public ModelAndView printFacebookAccess() {
    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_ACCESS.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookAccessPage(BACK_PAGE.FACEBOOK_ACCESS, Locale.FRANCE);
  }

  @RequestMapping(value = "/manager/facebook/import", method = RequestMethod.GET)
  public ModelAndView printFacebookImport() {

    LOGGER.info("Accès à la page " + BACK_PAGE.FACEBOOK_IMPORT.name());
    return facebookDisplayFactory.computeModelAndViewForFacebookImportPage(BACK_PAGE.FACEBOOK_IMPORT, Locale.FRANCE);
  }

}
