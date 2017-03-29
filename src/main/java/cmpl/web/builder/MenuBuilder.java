package cmpl.web.builder;

import java.util.List;
import java.util.Locale;

import cmpl.web.model.menu.MenuItem;

public interface MenuBuilder {

  List<MenuItem> computeMenuItems(Locale locale);

}
