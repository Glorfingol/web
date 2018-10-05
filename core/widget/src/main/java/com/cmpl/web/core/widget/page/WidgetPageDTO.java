package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WidgetPageDTO extends BaseDTO {

  private Long pageId;

  private Long widgetId;

  public Long getPageId() {
    return pageId;
  }

  public void setPageId(Long pageId) {
    this.pageId = pageId;
  }

  public Long getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(Long widgetId) {
    this.widgetId = widgetId;
  }
}
