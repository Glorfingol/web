package com.cmpl.web.core.role;

import java.util.Locale;

public interface RoleDispatcher {

  RoleResponse createEntity(RoleCreateForm form, Locale locale);

  RoleResponse updateEntity(RoleUpdateForm form, Locale locale);

}
