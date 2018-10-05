package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface WidgetPageService extends BaseService<WidgetPageDTO> {

  List<WidgetPageDTO> findByPageId(Long pageId);

  List<WidgetPageDTO> findByWidgetId(Long widgetId);

  WidgetPageDTO findByPageIdAndWidgetId(Long pageId, Long widgetId);

}
