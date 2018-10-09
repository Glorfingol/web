package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.dto.BaseDTO;

public class ResponsibilityDTO extends BaseDTO {

  private Long userId;

  private Long roleId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

}
