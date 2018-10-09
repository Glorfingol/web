package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface PrivilegeService extends BaseService<PrivilegeDTO> {

  List<PrivilegeDTO> findByRoleId(Long roleId);
}
