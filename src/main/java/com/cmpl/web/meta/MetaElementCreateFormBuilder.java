package com.cmpl.web.meta;

import com.cmpl.web.core.builder.Builder;

public class MetaElementCreateFormBuilder extends Builder<MetaElementCreateForm> {

  private String name = "";
  private String content = "";
  private String pageId;

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

}
