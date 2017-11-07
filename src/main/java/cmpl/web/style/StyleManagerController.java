package cmpl.web.style;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.BACK_PAGE;

@Controller
@RequestMapping(value = "/manager/styles")
public class StyleManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StyleManagerController.class);

  private final StyleDisplayFactory displayFactory;

  public StyleManagerController(StyleDisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping
  public ModelAndView printViewStyle() {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());
    return displayFactory.computeModelAndViewForViewStyles(BACK_PAGE.STYLES_VIEW, Locale.FRANCE);
  }

  @GetMapping(value = "/_edit")
  public ModelAndView printEditStyle() {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());
    return displayFactory.computeModelAndViewForUpdateStyles(BACK_PAGE.STYLES_UPDATE, Locale.FRANCE);
  }
}
