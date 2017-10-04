package cmpl.web.menu;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cmpl.web.page.BACK_PAGE;

@Controller
public class MenuManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MenuManagerController.class);

  private final MenuDispatcher dispatcher;
  private final MenuManagerDisplayFactory displayFactory;

  @Autowired
  public MenuManagerController(MenuDispatcher dispatcher, MenuManagerDisplayFactory displayFactory) {
    this.dispatcher = dispatcher;
    this.displayFactory = displayFactory;
  }

  @GetMapping(value = "/manager/menus")
  public ModelAndView printViewMenus(@RequestParam(name = "p", required = false) Integer pageNumber) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_VIEW.name());
    return displayFactory.computeModelAndViewForViewAllMenus(BACK_PAGE.MENUS_VIEW, Locale.FRANCE, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/manager/menus/_create")
  public ModelAndView printCreateMenu() {
    LOGGER.info("Accès à la page de création des menus");
    return displayFactory.computeModelAndViewForCreateMenu(BACK_PAGE.MENUS_CREATE, Locale.FRANCE);
  }

  @GetMapping(value = "/manager/menus/{menuId}")
  public ModelAndView printViewUpdateMenu(@PathVariable(value = "menuId") String menuId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_UPDATE.name() + " pour " + menuId);
    return displayFactory.computeModelAndViewForUpdateMenu(BACK_PAGE.MENUS_UPDATE, Locale.FRANCE, menuId);
  }
}
