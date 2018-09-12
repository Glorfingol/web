package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Membership;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends BaseRepository<Membership> {

  List<Membership> findByGroupId(Long groupId);

  List<Membership> findByEntityId(Long entityId);

  Membership findByEntityIdAndGroupId(Long entityId, Long groupId);

}
