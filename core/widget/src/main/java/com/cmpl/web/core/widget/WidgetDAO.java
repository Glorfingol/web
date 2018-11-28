package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Widget;
import java.util.List;

public interface WidgetDAO extends BaseDAO<Widget> {

  Widget findByName(String widgetName);

  List<Widget> findByEntityIdAndType(String entityId, String type);

}
