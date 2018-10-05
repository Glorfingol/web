package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.WidgetPage;
import java.util.List;

public interface WidgetPageDAO extends BaseDAO<WidgetPage> {

  List<WidgetPage> findByPageId(Long pageId);

  List<WidgetPage> findByWidgetId(Long widgetId);

  WidgetPage findByPageIdAndWidgetId(Long pageId, Long widgetId);

}
