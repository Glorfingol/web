package com.cmpl.web.core.user;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface UserValidator {

  Error validateCreate(UserCreateForm form, Locale locale);

  Error validateUpdate(UserUpdateForm form, Locale locale);

}
