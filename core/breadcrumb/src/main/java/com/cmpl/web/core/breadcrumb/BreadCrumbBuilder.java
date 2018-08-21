package com.cmpl.web.core.breadcrumb;

import com.cmpl.web.core.common.builder.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BreadCrumbBuilder extends Builder<BreadCrumb> {

  private List<BreadCrumbItem> items;

  private String pageName;

  private BreadCrumbBuilder() {
    items = new ArrayList<>();
  }

  public BreadCrumbBuilder items(List<BreadCrumbItem> items) {
    this.items = items;
    return this;
  }

  public BreadCrumbBuilder pageName(String pageName) {
    this.pageName = pageName;
    return this;
  }

  @Override
  public BreadCrumb build() {
    BreadCrumb breadCrumb = new BreadCrumb();
    breadCrumb.setItems(items.stream().collect(Collectors.toList()));
    breadCrumb.setPageName(pageName);
    return breadCrumb;
  }

  public static BreadCrumbBuilder create() {
    return new BreadCrumbBuilder();
  }

}
