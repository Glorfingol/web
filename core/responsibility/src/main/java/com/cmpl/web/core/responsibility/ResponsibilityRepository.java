package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Responsibility;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibilityRepository extends BaseRepository<Responsibility> {

  List<Responsibility> findByUserId(Long userId);

  List<Responsibility> findByRoleId(Long roleId);

  Responsibility findByUserIdAndRoleId(Long userId, Long roleId);

}
