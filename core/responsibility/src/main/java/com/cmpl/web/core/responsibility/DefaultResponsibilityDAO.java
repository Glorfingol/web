package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Responsibility;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultResponsibilityDAO extends DefaultBaseDAO<Responsibility> implements
  ResponsibilityDAO {

  private final ResponsibilityRepository responsibilityRepository;

  public DefaultResponsibilityDAO(ResponsibilityRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(Responsibility.class, entityRepository, publisher);
    this.responsibilityRepository = entityRepository;
  }

  @Override
  public List<Responsibility> findByUserId(Long userId) {
    return responsibilityRepository.findByUserId(userId);
  }

  @Override
  public List<Responsibility> findByRoleId(Long roleId) {
    return responsibilityRepository.findByRoleId(roleId);
  }

  @Override
  public Responsibility findByUserIdAndRoleId(Long userId, Long roleId) {
    return responsibilityRepository.findByUserIdAndRoleId(userId, roleId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
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
