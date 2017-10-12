package cmpl.web.menu;

import java.util.Locale;

import cmpl.web.core.model.Error;

public interface MenuValidator {

  Error validateCreate(MenuCreateForm form, Locale locale);

  Error validateUpdate(MenuUpdateForm form, Locale locale);
}
