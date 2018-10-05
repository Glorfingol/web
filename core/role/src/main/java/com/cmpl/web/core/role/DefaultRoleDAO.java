package com.cmpl.web.core.role;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QRole;
import com.cmpl.web.core.models.Role;
import com.querydsl.core.types.Predicate;
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
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    throw new UnsupportedOperationException();
  }
}
