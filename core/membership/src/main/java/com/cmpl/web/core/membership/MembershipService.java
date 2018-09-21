package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface MembershipService extends BaseService<MembershipDTO> {

  List<MembershipDTO> findByEntityId(Long entityId);

  List<MembershipDTO> findByGroupId(Long groupId);

  MembershipDTO findByEntityIdAndGroupId(Long entityId, Long groupId);

}
