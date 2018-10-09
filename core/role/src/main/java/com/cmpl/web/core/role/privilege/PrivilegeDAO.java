package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Privilege;
import java.util.List;

public interface PrivilegeDAO extends BaseDAO<Privilege> {

  List<Privilege> findByRoleId(Long roleId);
}
