package com.cmpl.web.page;

import java.util.Locale;

public interface PageDispatcher {

  PageResponse createEntity(PageCreateForm form, Locale locale);

  PageResponse updateEntity(PageUpdateForm form, Locale locale);
}
