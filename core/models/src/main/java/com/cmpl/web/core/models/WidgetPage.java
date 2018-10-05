package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name = "widget_page")
@Table(name = "widget_page", indexes = {
  @Index(name = "IDX_WIDGET_PAGE", columnList = "page_id,widget_id", unique = true)})
public class WidgetPage extends BaseEntity {

  @Column(name = "page_id", nullable = false, length = 20)
  private Long pageId;

  @Column(name = "widget_id", nullable = false, length = 20)
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
