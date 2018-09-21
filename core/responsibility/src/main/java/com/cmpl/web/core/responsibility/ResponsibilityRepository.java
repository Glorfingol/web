package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Responsibility;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibilityRepository extends BaseRepository<Responsibility> {

  List<Responsibility> findByUserId(String userId);

  List<Responsibility> findByRoleId(String roleId);

  Responsibility findByUserIdAndRoleId(String userId, String roleId);

}
