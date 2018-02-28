package com.cmpl.web.core.association_user_role;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class AssociationUserRoleServiceImpl extends BaseServiceImpl<AssociationUserRoleDTO, AssociationUserRole>
    implements AssociationUserRoleService {

  private final AssociationUserRoleRepository entityRepository;

  public AssociationUserRoleServiceImpl(AssociationUserRoleRepository entityRepository) {
    super(entityRepository);
    this.entityRepository = entityRepository;
  }

  @Override
  public List<AssociationUserRoleDTO> findByUserId(String userId) {
    return toListDTO(entityRepository.findByUserId(userId));
  }

  @Override
  public List<AssociationUserRoleDTO> findByRoleId(String roleId) {
    return toListDTO(entityRepository.findByRoleId(roleId));
  }

  @Override
  protected AssociationUserRoleDTO toDTO(AssociationUserRole entity) {
    AssociationUserRoleDTO dto = AssociationUserRoleDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected AssociationUserRole toEntity(AssociationUserRoleDTO dto) {
    AssociationUserRole entity = AssociationUserRoleBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
