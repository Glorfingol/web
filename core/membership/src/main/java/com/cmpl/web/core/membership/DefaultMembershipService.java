package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Membership;
import java.util.List;

public class DefaultMembershipService extends
    DefaultBaseService<MembershipDTO, Membership> implements
    MembershipService {

  private final MembershipDAO dao;

  public DefaultMembershipService(MembershipDAO dao, MembershipMapper mapper) {
    super(dao, mapper);
    this.dao = dao;
  }

  @Override
  public List<MembershipDTO> findByEntityId(Long entityId) {
    return mapper.toListDTO(dao.findByEntityId(entityId));
  }

  @Override
  public List<MembershipDTO> findByGroupId(Long groupId) {
    return mapper.toListDTO(dao.findByGroupId(groupId));
  }

  @Override
  public MembershipDTO findByEntityIdAndGroupId(Long entityId, Long groupId) {
    return mapper.toDTO(dao.findByEntityIdAndGroupId(entityId, groupId));
  }
}
