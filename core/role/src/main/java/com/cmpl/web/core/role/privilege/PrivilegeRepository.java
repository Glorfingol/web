package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Privilege;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends BaseRepository<Privilege> {

  List<Privilege> findByRoleId(Long roleId);
}
