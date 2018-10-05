package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Membership;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultMembershipDAO extends DefaultBaseDAO<Membership> implements MembershipDAO {

  private final MembershipRepository entityRepository;

  public DefaultMembershipDAO(MembershipRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(Membership.class, entityRepository, publisher);
    this.entityRepository = entityRepository;
  }

  @Override
  public List<Membership> findByEntityId(Long entityId) {
    return entityRepository.findByEntityId(entityId);
  }

  @Override
  public List<Membership> findByGroupId(Long groupId) {
    return entityRepository.findByGroupId(groupId);
  }

  @Override
  public Membership findByEntityIdAndGroupId(Long entityId, Long groupId) {
    return entityRepository.findByEntityIdAndGroupId(entityId, groupId);
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
