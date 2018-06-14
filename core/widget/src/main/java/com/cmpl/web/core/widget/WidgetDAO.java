package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface WidgetDAO extends BaseDAO<Widget> {

  Widget findByName(String widgetName);

}
