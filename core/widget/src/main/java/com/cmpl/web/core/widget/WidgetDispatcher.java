package com.cmpl.web.core.widget;

import java.util.Locale;

public interface WidgetDispatcher {


  WidgetResponse createEntity(WidgetCreateForm form, Locale locale);

  WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale);

}
