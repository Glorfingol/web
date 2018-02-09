package com.cmpl.web.core.widget;

public class WidgetCreateForm {

  private WIDGET_TYPE type;
  private String name;
  private String localeCode;

  public WIDGET_TYPE getType() {
    return type;
  }

  public void setType(WIDGET_TYPE type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }
}
