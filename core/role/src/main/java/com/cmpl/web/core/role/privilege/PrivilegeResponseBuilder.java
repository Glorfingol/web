package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.builder.Builder;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeResponseBuilder extends Builder<PrivilegeResponse> {

  List<PrivilegeDTO> privileges;

  private PrivilegeResponseBuilder() {
    privileges = new ArrayList<>();
  }

  public PrivilegeResponseBuilder privileges(List<PrivilegeDTO> privileges) {
    this.privileges.addAll(privileges);
    return this;
  }

  @Override
  public PrivilegeResponse build() {
    PrivilegeResponse response = new PrivilegeResponse();
    response.setPrivileges(privileges);
    return response;
  }

  public static PrivilegeResponseBuilder create() {
    return new PrivilegeResponseBuilder();
  }
}
