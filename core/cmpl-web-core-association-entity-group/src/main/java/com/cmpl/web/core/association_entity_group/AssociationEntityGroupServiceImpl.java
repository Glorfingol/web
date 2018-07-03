package com.cmpl.web.core.association_entity_group;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.AssociationEntityGroup;

public class AssociationEntityGroupServiceImpl extends
    BaseServiceImpl<AssociationEntityGroupDTO, AssociationEntityGroup> implements AssociationEntityGroupService {

  private final AssociationEntityGroupDAO dao;

  public AssociationEntityGroupServiceImpl(AssociationEntityGroupDAO dao, AssociationEntityGroupMapper mapper) {
    super(dao, mapper);
    this.dao = dao;
  }

  @Override
  public List<AssociationEntityGroupDTO> findByEntityId(Long entityId) {
    return mapper.toListDTO(dao.findByEntityId(entityId));
  }
}
