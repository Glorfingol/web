package com.cmpl.web.core.group;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class GroupServiceImpl extends BaseServiceImpl<GroupDTO, Group> implements GroupService {

  public GroupServiceImpl(GroupDAO groupDAO, GroupMapper groupMapper) {
    super(groupDAO, groupMapper);
  }

}
