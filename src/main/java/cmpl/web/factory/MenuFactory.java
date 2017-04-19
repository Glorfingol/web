package cmpl.web.factory;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.menu.MenuItem;

public interface MenuFactory {

  List<MenuItem> computeMenuItems(Locale locale);

  List<MenuItem> computeBackMenuItems(Locale locale);

}
