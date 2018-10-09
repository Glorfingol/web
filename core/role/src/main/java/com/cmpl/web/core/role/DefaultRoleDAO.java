package com.cmpl.web.core.role;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QResponsibility;
import com.cmpl.web.core.models.QRole;
import com.cmpl.web.core.models.Role;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultRoleDAO extends DefaultBaseDAO<Role> implements RoleDAO {

  public DefaultRoleDAO(RoleRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Role.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QRole role = QRole.role;
    return role.name.containsIgnoreCase(query);
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QRole qRole = QRole.role;
    QResponsibility qResponsibility = QResponsibility.responsibility;
    return qRole.id.in(new JPAQuery<>().from(qResponsibility).select(qResponsibility.roleId)
      .where(qResponsibility.userId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QRole qRole = QRole.role;
    QResponsibility qResponsibility = QResponsibility.responsibility;
    return qRole.id.notIn(new JPAQuery<>().from(qResponsibility).select(qResponsibility.roleId)
      .where(qResponsibility.userId.eq(notLinkedToId))
      .distinct());
  }
}
