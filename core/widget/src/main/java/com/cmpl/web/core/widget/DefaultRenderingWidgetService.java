package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.Widget;

public class DefaultRenderingWidgetService extends
  DefaultReadOnlyService<RenderingWidgetDTO, Widget> implements RenderingWidgetService {

  public DefaultRenderingWidgetService(WidgetDAO dao, RenderingWidgetMapper mapper) {
    super(dao, mapper);
  }
}
