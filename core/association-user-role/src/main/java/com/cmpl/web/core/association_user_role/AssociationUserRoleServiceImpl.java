package com.cmpl.web.core.association_user_role;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.AssociationUserRole;

public class AssociationUserRoleServiceImpl extends BaseServiceImpl<AssociationUserRoleDTO, AssociationUserRole>
    implements AssociationUserRoleService {

  private final AssociationUserRoleDAO associationUserRoleDAO;

  public AssociationUserRoleServiceImpl(AssociationUserRoleDAO associationUserRoleDAO,
      AssociationUserRoleMapper associationUserRoleMapper) {
    super(associationUserRoleDAO, associationUserRoleMapper);
    this.associationUserRoleDAO = associationUserRoleDAO;
  }

  @Override
  public List<AssociationUserRoleDTO> findByUserId(String userId) {
    return mapper.toListDTO(associationUserRoleDAO.findByUserId(userId));
  }

  @Override
  public List<AssociationUserRoleDTO> findByRoleId(String roleId) {
    return mapper.toListDTO(associationUserRoleDAO.findByRoleId(roleId));
  }

  @Override
  public AssociationUserRoleDTO findByUserIdAndRoleId(String userId, String roleId) {
    return mapper.toDTO(associationUserRoleDAO.findByUserIdAndRoleId(userId, roleId));
  }

}
