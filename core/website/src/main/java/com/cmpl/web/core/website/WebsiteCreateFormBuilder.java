package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.Builder;

public class WebsiteCreateFormBuilder extends Builder<WebsiteCreateForm> {

  private String name;

  private String description;

  private String extension;


  private Boolean secure;

  private Boolean systemJquery;

  private Boolean systemBootstrap;

  private Boolean systemFontAwesome;

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

  public WebsiteCreateFormBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  public WebsiteCreateFormBuilder systemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
    return this;
  }

  public WebsiteCreateFormBuilder systemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
    return this;
  }

  public WebsiteCreateFormBuilder systemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
    return this;
  }

  @Override
  public WebsiteCreateForm build() {
    WebsiteCreateForm form = new WebsiteCreateForm();
    form.setDescription(description);
    form.setName(name);
    form.setExtension(extension);
    form.setSecure(secure);
    form.setSystemBootstrap(systemBootstrap);
    form.setSystemFontAwesome(systemFontAwesome);
    form.setSystemJquery(systemJquery);
    return form;
  }

  public static WebsiteCreateFormBuilder create() {
    return new WebsiteCreateFormBuilder();
  }
}
