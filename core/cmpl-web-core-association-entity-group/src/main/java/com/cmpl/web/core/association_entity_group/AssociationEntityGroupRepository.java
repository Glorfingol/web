package com.cmpl.web.core.association_entity_group;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.AssociationEntityGroup;

@Repository
public interface AssociationEntityGroupRepository extends BaseRepository<AssociationEntityGroup> {

  List<AssociationEntityGroup> findByEntityId(Long entityId);

}
