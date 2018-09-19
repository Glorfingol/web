package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.Builder;

public class WebsiteCreateFormBuilder extends Builder<WebsiteCreateForm> {

  private String name;

  private String description;

  private String extension;

  private WebsiteCreateFormBuilder() {

  }

  public WebsiteCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteCreateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteCreateFormBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  @Override
  public WebsiteCreateForm build() {
    WebsiteCreateForm form = new WebsiteCreateForm();
    form.setDescription(description);
    form.setName(name);
    form.setExtension(extension);
    return form;
  }

  public static WebsiteCreateFormBuilder create() {
    return new WebsiteCreateFormBuilder();
  }
}
