package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface ResponsibilityService extends BaseService<ResponsibilityDTO> {

  List<ResponsibilityDTO> findByUserId(String userId);

  List<ResponsibilityDTO> findByRoleId(String roleId);

  ResponsibilityDTO findByUserIdAndRoleId(String userId, String roleId);

}
