package com.cmpl.web.core.group;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class GroupUpdateForm extends BaseUpdateForm<GroupDTO> {

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
