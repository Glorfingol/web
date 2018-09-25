package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.service.ReadOnlyService;

public interface RenderingWidgetService extends ReadOnlyService<RenderingWidgetDTO> {

  RenderingWidgetDTO findByName(String widgetName);

}
