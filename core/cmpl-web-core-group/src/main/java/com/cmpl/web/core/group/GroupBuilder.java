package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.BOGroup;

public class GroupBuilder extends BaseBuilder<BOGroup> {

  private String name;

  private String description;

  private GroupBuilder() {

  }

  public GroupBuilder description(String description) {
    this.description = description;
    return this;
  }

  public GroupBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public BOGroup build() {
    BOGroup BOGroup = new BOGroup();
    BOGroup.setDescription(description);
    BOGroup.setName(name);
    BOGroup.setId(id);
    BOGroup.setCreationDate(creationDate);
    BOGroup.setModificationDate(modificationDate);
    BOGroup.setCreationUser(creationUser);
    BOGroup.setModificationUser(modificationUser);
    return BOGroup;
  }

  public static GroupBuilder create() {
    return new GroupBuilder();
  }
}
