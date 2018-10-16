package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dto.BaseDTO;

public class RenderingWebsiteDTO extends BaseDTO {

  private String name;

  private String extension;

  private String description;

  private boolean secure;

  private boolean systemJquery;

  private boolean systemBootstrap;

  private boolean systemFontAwesome;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public boolean isSystemJquery() {
    return systemJquery;
  }

  public void setSystemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
  }

  public boolean isSystemBootstrap() {
    return systemBootstrap;
  }

  public void setSystemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
  }

  public boolean isSystemFontAwesome() {
    return systemFontAwesome;
  }

  public void setSystemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
  }
}
