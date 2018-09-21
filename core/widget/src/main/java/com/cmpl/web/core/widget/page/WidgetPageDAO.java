package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.WidgetPage;
import java.util.List;

public interface WidgetPageDAO extends BaseDAO<WidgetPage> {

  List<WidgetPage> findByPageId(String pageId);

  List<WidgetPage> findByWidgetId(String widgetId);

  WidgetPage findByPageIdAndWidgetId(String pageId, String widgetId);

}
