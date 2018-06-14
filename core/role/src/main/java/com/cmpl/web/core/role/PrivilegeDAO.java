package com.cmpl.web.core.role;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface PrivilegeDAO extends BaseDAO<Privilege> {

  List<Privilege> findByRoleId(String roleId);
}
