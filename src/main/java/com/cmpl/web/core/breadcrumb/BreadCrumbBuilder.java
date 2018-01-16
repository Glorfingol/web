package com.cmpl.web.core.breadcrumb;

import java.util.List;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.page.BACK_PAGE;

public class BreadCrumbBuilder extends Builder<BreadCrumb> {

  private List<BreadCrumbItem> items;
  private BACK_PAGE page;

  private BreadCrumbBuilder() {

  }

  public BreadCrumbBuilder items(List<BreadCrumbItem> items) {
    this.items = items;
    return this;
  }

  public BreadCrumbBuilder page(BACK_PAGE page) {
    this.page = page;
    return this;
  }

  @Override
  public BreadCrumb build() {
    BreadCrumb breadCrumb = new BreadCrumb();
    breadCrumb.setItems(items);
    breadCrumb.setPage(page);
    return breadCrumb;
  }

  public static BreadCrumbBuilder create() {
    return new BreadCrumbBuilder();
  }

}
