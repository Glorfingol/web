package com.cmpl.web.core.group;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface GroupValidator {

  Error validateCreate(GroupCreateForm form, Locale locale);

  Error validateUpdate(GroupUpdateForm form, Locale locale);

}
