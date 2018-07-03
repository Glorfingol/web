package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.AssociationUserRole;

public class AssociationUserRoleMapper extends BaseMapper<AssociationUserRoleDTO, AssociationUserRole> {

  @Override
  public AssociationUserRoleDTO toDTO(AssociationUserRole entity) {
    AssociationUserRoleDTO dto = AssociationUserRoleDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public AssociationUserRole toEntity(AssociationUserRoleDTO dto) {
    AssociationUserRole entity = AssociationUserRoleBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
