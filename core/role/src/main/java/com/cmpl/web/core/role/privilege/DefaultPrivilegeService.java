package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Privilege;
import java.util.List;

public class DefaultPrivilegeService extends DefaultBaseService<PrivilegeDTO, Privilege> implements
  PrivilegeService {

  private final PrivilegeDAO privilegeDAO;

  public DefaultPrivilegeService(PrivilegeDAO privilegeDAO, PrivilegeMapper privilegeMapper) {
    super(privilegeDAO, privilegeMapper);
    this.privilegeDAO = privilegeDAO;
  }

  @Override
  public List<PrivilegeDTO> findByRoleId(Long roleId) {
    return mapper.toListDTO(privilegeDAO.findByRoleId(roleId));
  }
}
