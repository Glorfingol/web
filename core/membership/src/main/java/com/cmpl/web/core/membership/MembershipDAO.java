package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Membership;
import java.util.List;

public interface MembershipDAO extends BaseDAO<Membership> {

  List<Membership> findByEntityId(Long entityId);

  Membership findByEntityIdAndGroupId(Long entityId, Long groupId);

}
