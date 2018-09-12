package com.cmpl.web.core.group;

import com.cmpl.web.core.common.dto.BaseDTO;
import java.util.Objects;

public class GroupDTO extends BaseDTO {

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

  @Override
  public boolean equals(Object o) {
    boolean isSameBase = super.equals(o);
    GroupDTO groupDTO = (GroupDTO) o;
    return isSameBase && Objects.equals(name, groupDTO.name) &&
        Objects.equals(description, groupDTO.description);
  }


}
