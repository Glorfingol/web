package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WidgetDTO extends BaseDTO {

  private WIDGET_TYPE type;

  private String entityId;

  private String name;

  private String personalization;

  public WIDGET_TYPE getType() {
    return type;
  }

  public void setType(WIDGET_TYPE type) {
    this.type = type;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPersonalization() {
    return personalization;
  }

  public void setPersonalization(String personalization) {
    this.personalization = personalization;
  }
}
