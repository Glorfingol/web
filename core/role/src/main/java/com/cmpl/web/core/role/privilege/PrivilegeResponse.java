package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.resource.BaseResponse;
import java.util.List;

public class PrivilegeResponse extends BaseResponse {

  List<PrivilegeDTO> privileges;

  public List<PrivilegeDTO> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<PrivilegeDTO> privileges) {
    this.privileges = privileges;
  }
}
