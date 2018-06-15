package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class GroupResponseBuilder extends Builder<GroupResponse> {

  private GroupDTO group;
  private Error error;

  public GroupResponseBuilder group(GroupDTO role) {
    this.group = group;
    return this;
  }

  public GroupResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  private GroupResponseBuilder() {

  }

  @Override
  public GroupResponse build() {
    GroupResponse response = new GroupResponse();
    response.setGroup(group);
    response.setError(error);

    return response;
  }

  public static GroupResponseBuilder create() {
    return new GroupResponseBuilder();
  }

}
