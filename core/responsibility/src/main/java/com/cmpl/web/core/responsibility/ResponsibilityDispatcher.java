package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

public interface ResponsibilityDispatcher {

  ResponsibilityResponse createEntity(ResponsibilityCreateForm createForm, Locale locale)
      throws BaseException;

  void deleteEntity(String userId, String roleId, Locale locale) throws BaseException;

}
