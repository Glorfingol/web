package cmpl.web.factory;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.page.BACK_PAGE;
import cmpl.web.model.page.PAGE;

public interface MenuFactory {

  List<MenuItem> computeMenuItems(PAGE page, Locale locale);

  List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale);

}
