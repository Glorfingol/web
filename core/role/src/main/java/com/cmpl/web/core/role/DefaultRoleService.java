package com.cmpl.web.core.role;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Role;

public class DefaultRoleService extends DefaultBaseService<RoleDTO, Role> implements RoleService {

  public DefaultRoleService(RoleDAO roleDAO, RoleMapper roleMapper) {
    super(roleDAO, roleMapper);
  }

}
