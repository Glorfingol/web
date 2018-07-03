package com.cmpl.web.core.association_entity_group;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface AssociationEntityGroupService extends BaseService<AssociationEntityGroupDTO> {

  List<AssociationEntityGroupDTO> findByEntityId(Long entityId);

}
