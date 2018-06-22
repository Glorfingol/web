package com.cmpl.web.core.association_entity_group;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class AssociationEntityGroupBuilder extends BaseBuilder<AssociationEntityGroup> {

  private String groupId;
  private String entityId;

  public AssociationEntityGroupBuilder groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  public AssociationEntityGroupBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  private AssociationEntityGroupBuilder() {

  }

  @Override
  public AssociationEntityGroup build() {
    AssociationEntityGroup associationUserRole = new AssociationEntityGroup();
    associationUserRole.setGroupId(groupId);
    associationUserRole.setEntityId(entityId);
    return associationUserRole;
  }

  public static AssociationEntityGroupBuilder create() {
    return new AssociationEntityGroupBuilder();
  }
}
