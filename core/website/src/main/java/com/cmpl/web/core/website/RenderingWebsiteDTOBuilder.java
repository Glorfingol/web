package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RenderingWebsiteDTOBuilder extends BaseBuilder<RenderingWebsiteDTO> {

  private String name;

  private String extension;

  private String description;

  private boolean secure;

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

  @Override
  public RenderingWebsiteDTO build() {
    RenderingWebsiteDTO website = new RenderingWebsiteDTO();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    website.setExtension(extension);
    return website;
  }

  public static RenderingWebsiteDTOBuilder create() {
    return new RenderingWebsiteDTOBuilder();
  }
}
