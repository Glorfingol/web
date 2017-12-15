package com.cmpl.web.menu;

import java.util.Locale;

import com.cmpl.web.core.model.Error;

public interface MenuValidator {

  Error validateCreate(MenuCreateForm form, Locale locale);

  Error validateUpdate(MenuUpdateForm form, Locale locale);
}
