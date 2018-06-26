package com.cmpl.web.core.association_user_role;

import javax.validation.constraints.NotBlank;

public class AssociationUserRoleCreateForm {

  @NotBlank(message = "empty.user.id")
  private String userId;

  @NotBlank(message = "empty.role.id")
  private String roleId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}
