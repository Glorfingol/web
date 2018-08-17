package com.cmpl.web.core.group;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.models.QBOGroup;
import com.querydsl.core.types.Predicate;

public class GroupDAOImpl extends BaseDAOImpl<BOGroup> implements GroupDAO {

  public GroupDAOImpl(GroupRepository entityRepository, ApplicationEventPublisher publisher) {
    super(BOGroup.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QBOGroup qboGroup = QBOGroup.bOGroup;
    return qboGroup.name.containsIgnoreCase(query);
  }
}
