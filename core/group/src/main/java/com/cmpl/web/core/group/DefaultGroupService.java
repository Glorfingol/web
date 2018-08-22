package com.cmpl.web.core.group;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.BOGroup;

public class DefaultGroupService extends DefaultBaseService<GroupDTO, BOGroup> implements
    GroupService {

  public DefaultGroupService(GroupDAO groupDAO, GroupMapper groupMapper) {
    super(groupDAO, groupMapper);
  }

}
