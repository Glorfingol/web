package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Responsibility;
import java.util.List;

public interface ResponsibilityDAO extends BaseDAO<Responsibility> {

  List<Responsibility> findByUserId(Long userId);

  List<Responsibility> findByRoleId(Long roleId);

  Responsibility findByUserIdAndRoleId(Long userId, Long roleId);

}
