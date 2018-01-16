package com.cmpl.web.meta;

import com.cmpl.web.core.builder.Builder;

public class OpenGraphMetaElementCreateFormBuilder extends Builder<OpenGraphMetaElementCreateForm> {

  private String property;
  private String content;
  private String pageId;

  private OpenGraphMetaElementCreateFormBuilder() {

  }

  public OpenGraphMetaElementCreateFormBuilder property(String property) {
    this.property = property;
    return this;
  }

  public OpenGraphMetaElementCreateFormBuilder content(String content) {
    this.content = content;
    return this;
  }

  public OpenGraphMetaElementCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public OpenGraphMetaElementCreateForm build() {
    OpenGraphMetaElementCreateForm form = new OpenGraphMetaElementCreateForm();
    form.setProperty(property);
    form.setContent(content);
    form.setPageId(pageId);
    return form;
  }

  public static OpenGraphMetaElementCreateFormBuilder create() {
    return new OpenGraphMetaElementCreateFormBuilder();
  }

}
