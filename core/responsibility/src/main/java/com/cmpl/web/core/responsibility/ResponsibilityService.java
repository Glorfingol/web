package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface ResponsibilityService extends BaseService<ResponsibilityDTO> {

  List<ResponsibilityDTO> findByUserId(Long userId);

  List<ResponsibilityDTO> findByRoleId(Long roleId);

  ResponsibilityDTO findByUserIdAndRoleId(Long userId, Long roleId);

}
