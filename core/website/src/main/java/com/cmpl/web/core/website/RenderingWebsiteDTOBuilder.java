package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RenderingWebsiteDTOBuilder extends BaseBuilder<RenderingWebsiteDTO> {

  private String name;

  private String extension;

  private String description;

  private boolean secure;

  private boolean systemJquery;

  private boolean systemBootstrap;

  private boolean systemFontAwesome;

  private RenderingWebsiteDTOBuilder() {

  }

  public RenderingWebsiteDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RenderingWebsiteDTOBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public RenderingWebsiteDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RenderingWebsiteDTOBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }


  public RenderingWebsiteDTOBuilder systemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
    return this;
  }


  public RenderingWebsiteDTOBuilder systemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
    return this;
  }


  public RenderingWebsiteDTOBuilder systemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
    return this;
  }

  @Override
  public RenderingWebsiteDTO build() {
    RenderingWebsiteDTO website = new RenderingWebsiteDTO();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    website.setExtension(extension);
    website.setSystemBootstrap(systemBootstrap);
    website.setSystemJquery(systemJquery);
    website.setSystemFontAwesome(systemFontAwesome);
    return website;
  }

  public static RenderingWebsiteDTOBuilder create() {
    return new RenderingWebsiteDTOBuilder();
  }
}
