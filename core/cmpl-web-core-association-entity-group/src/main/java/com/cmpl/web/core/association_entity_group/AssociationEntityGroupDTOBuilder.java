package com.cmpl.web.core.association_entity_group;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class AssociationEntityGroupDTOBuilder extends BaseBuilder<AssociationEntityGroupDTO> {

  private Long groupId;
  private Long entityId;

  public AssociationEntityGroupDTOBuilder groupId(Long groupId) {
    this.groupId = groupId;
    return this;
  }

  public AssociationEntityGroupDTOBuilder entityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  private AssociationEntityGroupDTOBuilder() {

  }

  @Override
  public AssociationEntityGroupDTO build() {
    AssociationEntityGroupDTO associationEntityGroupDTO = new AssociationEntityGroupDTO();
    associationEntityGroupDTO.setGroupId(groupId);
    associationEntityGroupDTO.setEntityId(entityId);
    return associationEntityGroupDTO;
  }

  public static AssociationEntityGroupDTOBuilder create() {
    return new AssociationEntityGroupDTOBuilder();
  }
}
