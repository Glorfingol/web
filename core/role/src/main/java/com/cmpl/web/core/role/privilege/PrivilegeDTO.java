package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.dto.BaseDTO;

public class PrivilegeDTO extends BaseDTO {

  private Long roleId;

  private String content;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
