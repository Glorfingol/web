package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageResponse;
import java.util.Locale;

public interface WidgetDispatcher {

  WidgetResponse createEntity(WidgetCreateForm form, String personalization);

  WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale);

  WidgetResponse deleteEntity(String widgetId, Locale locale);

  WidgetPageResponse createEntity(String pageId, WidgetPageCreateForm form, Locale locale);

  void deleteEntity(String pageId, String widgetId, Locale locale) throws BaseException;

}
