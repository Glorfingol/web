package com.cmpl.web.core.meta;

import com.cmpl.web.core.common.builder.Builder;

public class MetaElementCreateFormBuilder extends Builder<MetaElementCreateForm> {

  private String name = "";
  private String content = "";
  private String pageId;

  private MetaElementCreateFormBuilder() {

  }

  public MetaElementCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementCreateFormBuilder content(String content) {
    this.content = content;
    return this;
  }

  public MetaElementCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public MetaElementCreateForm build() {
    MetaElementCreateForm form = new MetaElementCreateForm();
    form.setName(name);
    form.setContent(content);
    form.setPageId(pageId);
    return form;
  }

  public static MetaElementCreateFormBuilder create() {
    return new MetaElementCreateFormBuilder();
  }

}
