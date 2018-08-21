package com.cmpl.web.core.group;

import com.cmpl.web.core.common.resource.BaseResponse;
import java.util.Locale;

public interface GroupDispatcher {

  GroupResponse createEntity(GroupCreateForm form, Locale locale);

  GroupResponse updateEntity(GroupUpdateForm form, Locale locale);

  BaseResponse deleteEntity(String groupId, Locale locale);

}
