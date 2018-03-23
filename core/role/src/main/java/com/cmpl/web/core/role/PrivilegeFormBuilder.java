package com.cmpl.web.core.role;

import java.util.Map;

import com.cmpl.web.core.common.builder.Builder;

public class PrivilegeFormBuilder extends Builder<PrivilegeForm> {

  private Map<String, Boolean> privileges;

  public PrivilegeFormBuilder privileges(Map<String, Boolean> privileges) {
    this.privileges = privileges;
    return this;
  }

  private PrivilegeFormBuilder() {

  }

  @Override
  public PrivilegeForm build() {
    return new PrivilegeForm(privileges);
  }

  public static PrivilegeFormBuilder create() {
    return new PrivilegeFormBuilder();
  }
}
