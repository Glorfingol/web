package com.cmpl.web.core.group;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;

public class GroupDAOImpl extends BaseDAOImpl<Group> implements GroupDAO {

  public GroupDAOImpl(GroupRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Group.class, entityRepository, publisher);
  }
}
