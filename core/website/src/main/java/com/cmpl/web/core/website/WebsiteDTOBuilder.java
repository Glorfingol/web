package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WebsiteDTOBuilder extends BaseBuilder<WebsiteDTO> {

  private String name;

  private String extension;

  private String description;

  private boolean secure;

  private boolean systemJquery;

  private boolean systemBootstrap;

  private boolean systemFontAwesome;

  private WebsiteDTOBuilder() {

  }

  public WebsiteDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteDTOBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public WebsiteDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteDTOBuilder systemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
    return this;
  }

  public WebsiteDTOBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  public WebsiteDTOBuilder systemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
    return this;
  }

  public WebsiteDTOBuilder systemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
    return this;
  }

  @Override
  public WebsiteDTO build() {
    WebsiteDTO website = new WebsiteDTO();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    website.setExtension(extension);
    website.setSystemBootstrap(systemBootstrap);
    website.setSystemJquery(systemJquery);
    website.setSystemFontAwesome(systemFontAwesome);
    return website;
  }

  public static WebsiteDTOBuilder create() {
    return new WebsiteDTOBuilder();
  }
}
