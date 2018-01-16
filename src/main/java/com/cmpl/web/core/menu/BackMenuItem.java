package com.cmpl.web.core.menu;


public class BackMenuItem implements BackMenuItemPlugin {

  private String title;
  private String label;
  private String href;
  private int order;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @Override
  public boolean supports(String delimiter) {
    return true;
  }

}
