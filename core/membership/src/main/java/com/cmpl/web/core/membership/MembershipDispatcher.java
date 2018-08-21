package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;

public interface MembershipDispatcher {

  MembershipResponse createEntity(MembershipCreateForm createForm, Locale locale)
      throws BaseException;

  void deleteEntity(String entityId, String groupId, Locale locale) throws BaseException;

}
