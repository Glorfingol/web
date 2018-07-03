package com.cmpl.web.core.association_entity_group;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.AssociationEntityGroup;

public class AssociationEntityGroupDAOImpl extends BaseDAOImpl<AssociationEntityGroup>
    implements AssociationEntityGroupDAO {

  private final AssociationEntityGroupRepository entityRepository;

  public AssociationEntityGroupDAOImpl(AssociationEntityGroupRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(AssociationEntityGroup.class, entityRepository, publisher);
    this.entityRepository = entityRepository;
  }

  @Override
  public List<AssociationEntityGroup> findByEntityId(Long entityId) {
    return entityRepository.findByEntityId(entityId);
  }
}
