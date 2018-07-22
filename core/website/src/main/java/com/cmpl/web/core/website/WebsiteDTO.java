package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WebsiteDTO extends BaseDTO {

  private String name;

  private String description;

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
}
