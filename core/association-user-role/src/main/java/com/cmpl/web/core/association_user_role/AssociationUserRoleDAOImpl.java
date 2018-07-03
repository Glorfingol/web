package com.cmpl.web.core.association_user_role;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.AssociationUserRole;

public class AssociationUserRoleDAOImpl extends BaseDAOImpl<AssociationUserRole> implements AssociationUserRoleDAO {

  private final AssociationUserRoleRepository associationUserRoleRepository;

  public AssociationUserRoleDAOImpl(AssociationUserRoleRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(AssociationUserRole.class, entityRepository, publisher);
    this.associationUserRoleRepository = entityRepository;
  }

  @Override
  public List<AssociationUserRole> findByUserId(String userId) {
    return associationUserRoleRepository.findByUserId(userId);
  }

  @Override
  public List<AssociationUserRole> findByRoleId(String roleId) {
    return associationUserRoleRepository.findByRoleId(roleId);
  }

  @Override
  public AssociationUserRole findByUserIdAndRoleId(String userId, String roleId) {
    return associationUserRoleRepository.findByUserIdAndRoleId(userId, roleId);
  }
}
