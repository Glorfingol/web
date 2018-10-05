package com.cmpl.web.core.group;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.models.QBOGroup;
import com.cmpl.web.core.models.QMembership;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultGroupDAO extends DefaultBaseDAO<BOGroup> implements GroupDAO {

  public DefaultGroupDAO(GroupRepository entityRepository, ApplicationEventPublisher publisher) {
    super(BOGroup.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QBOGroup qboGroup = QBOGroup.bOGroup;
    return qboGroup.name.containsIgnoreCase(query);
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QBOGroup qboGroup = QBOGroup.bOGroup;
    QMembership qMembership = QMembership.membership;
    return qboGroup.id.in(new JPAQuery<>().from(qMembership).select(qMembership.groupId)
      .where(qMembership.entityId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QBOGroup qboGroup = QBOGroup.bOGroup;
    QMembership qMembership = QMembership.membership;
    return qboGroup.id.notIn(new JPAQuery<>().from(qMembership).select(qMembership.groupId)
      .where(qMembership.entityId.eq(notLinkedToId))
      .distinct());
  }
}
