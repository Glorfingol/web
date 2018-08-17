package com.cmpl.web.core.role;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Role;

public class RoleServiceImpl extends BaseServiceImpl<RoleDTO, Role> implements RoleService {

  public RoleServiceImpl(RoleDAO roleDAO, RoleMapper roleMapper) {
    super(roleDAO, roleMapper);
  }

}
