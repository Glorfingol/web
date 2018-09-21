package com.cmpl.web.core.website;

import com.cmpl.web.core.common.resource.BaseResponse;
import java.util.Locale;

public interface WebsiteDispatcher {

  WebsiteResponse createEntity(WebsiteCreateForm form, Locale locale);

  WebsiteResponse updateEntity(WebsiteUpdateForm form, Locale locale);

  BaseResponse deleteEntity(String roleId, Locale locale);

}
