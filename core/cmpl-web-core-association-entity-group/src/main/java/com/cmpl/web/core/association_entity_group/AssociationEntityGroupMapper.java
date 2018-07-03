package com.cmpl.web.core.association_entity_group;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.AssociationEntityGroup;

public class AssociationEntityGroupMapper extends BaseMapper<AssociationEntityGroupDTO, AssociationEntityGroup> {

  @Override
  public AssociationEntityGroupDTO toDTO(AssociationEntityGroup entity) {
    AssociationEntityGroupDTO dto = AssociationEntityGroupDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public AssociationEntityGroup toEntity(AssociationEntityGroupDTO dto) {
    AssociationEntityGroup entity = AssociationEntityGroupBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
