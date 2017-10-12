package cmpl.web.menu;

import java.util.Locale;

public interface MenuDispatcher {

  MenuResponse createEntity(MenuCreateForm form, Locale locale);

  MenuResponse updateEntity(MenuUpdateForm form, Locale locale);

}
