package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WebsiteDTO extends BaseDTO {

  private String name;

  private String extension;

  private String description;

  private boolean secure;

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
