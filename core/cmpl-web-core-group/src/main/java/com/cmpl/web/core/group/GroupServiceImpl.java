package com.cmpl.web.core.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class GroupServiceImpl extends BaseServiceImpl<GroupDTO, Group> implements GroupService {

  public GroupServiceImpl(GroupDAO groupDAO, GroupMapper groupMapper) {
    super(groupDAO, groupMapper);
  }

  @Override
  public Page<GroupDTO> searchEntities(PageRequest pageRequest, String query) {
    return null;
  }
}
