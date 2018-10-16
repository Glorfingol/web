package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Website;

public class WebsiteBuilder extends BaseBuilder<Website> {

  private String name;

  private String description;

  private String extension;

  private boolean systemJquery;

  private boolean systemBootstrap;

  private boolean systemFontAwesome;

  private boolean secure;

  private WebsiteBuilder() {

  }

  public WebsiteBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public WebsiteBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  public WebsiteBuilder systemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
    return this;
  }

  public WebsiteBuilder systemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
    return this;
  }

  public WebsiteBuilder systemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
    return this;
  }

  @Override
  public Website build() {
    Website website = new Website();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    website.setExtension(extension);
    website.setSystemBootstrap(systemBootstrap);
    website.setSystemJquery(systemJquery);
    website.setSystemFontAwesome(systemFontAwesome);
    return website;
  }

  public static WebsiteBuilder create() {
    return new WebsiteBuilder();
  }
}
