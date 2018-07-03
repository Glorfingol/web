package com.cmpl.web.core.association_entity_group;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.AssociationEntityGroup;

public class AssociationEntityGroupBuilder extends BaseBuilder<AssociationEntityGroup> {

  private Long groupId;
  private Long entityId;

  public AssociationEntityGroupBuilder groupId(Long groupId) {
    this.groupId = groupId;
    return this;
  }

  public AssociationEntityGroupBuilder entityId(Long entityId) {
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
