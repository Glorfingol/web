package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class GroupBuilder extends BaseBuilder<Group> {

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
  public Group build() {
    Group group = new Group();
    group.setDescription(description);
    group.setName(name);
    group.setId(id);
    group.setCreationDate(creationDate);
    group.setModificationDate(modificationDate);
    group.setCreationUser(creationUser);
    group.setModificationUser(modificationUser);
    return group;
  }

  public static GroupBuilder create() {
    return new GroupBuilder();
  }
}
