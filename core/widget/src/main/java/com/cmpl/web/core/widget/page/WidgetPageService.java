package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface WidgetPageService extends BaseService<WidgetPageDTO> {

  List<WidgetPageDTO> findByPageId(String pageId);

  List<WidgetPageDTO> findByWidgetId(String widgetId);

  WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId);

}
