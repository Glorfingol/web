package com.cmpl.web.core.association_user_role;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface AssociationUserRoleDAO extends BaseDAO<AssociationUserRole> {

  List<AssociationUserRole> findByUserId(String userId);

  List<AssociationUserRole> findByRoleId(String roleId);

  AssociationUserRole findByUserIdAndRoleId(String userId, String roleId);

}
