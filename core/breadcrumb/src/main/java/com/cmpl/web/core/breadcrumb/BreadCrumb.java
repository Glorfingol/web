package com.cmpl.web.core.breadcrumb;

import java.util.List;

public class BreadCrumb implements BreadCrumbPlugin {

  private List<BreadCrumbItem> items;

  private String pageName;

  public List<BreadCrumbItem> getItems() {
    return items;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public void setItems(List<BreadCrumbItem> items) {
    this.items = items;
  }

  @Override
  public boolean supports(String delimiter) {
    return pageName.equals(delimiter);
  }

}
