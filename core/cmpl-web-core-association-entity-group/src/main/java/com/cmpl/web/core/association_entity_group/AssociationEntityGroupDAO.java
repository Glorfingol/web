package com.cmpl.web.core.association_entity_group;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.AssociationEntityGroup;

public interface AssociationEntityGroupDAO extends BaseDAO<AssociationEntityGroup> {

  List<AssociationEntityGroup> findByEntityId(Long entityId);

}
