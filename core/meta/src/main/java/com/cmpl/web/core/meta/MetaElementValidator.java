package com.cmpl.web.core.meta;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface MetaElementValidator {

  Error validateCreate(String pageId, MetaElementCreateForm form, Locale locale);

  Error validateCreate(String pageId, OpenGraphMetaElementCreateForm form, Locale locale);

  Error validateDelete(String id, Locale locale);

}
