package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Privilege;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultPrivilegeDAO extends DefaultBaseDAO<Privilege> implements PrivilegeDAO {

  private final PrivilegeRepository privilegeRepository;

  public DefaultPrivilegeDAO(PrivilegeRepository privilegeRepository,
      ApplicationEventPublisher publisher) {
    super(Privilege.class, privilegeRepository, publisher);
    this.privilegeRepository = privilegeRepository;
  }

  @Override
  public List<Privilege> findByRoleId(String roleId) {
    return privilegeRepository.findByRoleId(roleId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }
}