package com.cmpl.web.core.website;

import javax.validation.constraints.NotBlank;

public class WebsiteCreateForm {

  @NotBlank(message = "empty.website.name")
  private String name;
  @NotBlank(message = "empty.website.description")
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
