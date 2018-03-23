package com.cmpl.web.core.role;

import java.util.HashMap;
import java.util.Map;

public class PrivilegeForm {

  private Map<String, Boolean> privileges;

  public PrivilegeForm() {

  }

  public PrivilegeForm(Map<String, Boolean> privileges) {
    this.privileges = new HashMap<>();
    this.privileges.putAll(privileges);
  }

  public Map<String, Boolean> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Map<String, Boolean> privileges) {
    this.privileges = privileges;
  }
}
