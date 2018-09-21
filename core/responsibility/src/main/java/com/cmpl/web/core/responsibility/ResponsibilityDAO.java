package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Responsibility;
import java.util.List;

public interface ResponsibilityDAO extends BaseDAO<Responsibility> {

  List<Responsibility> findByUserId(String userId);

  List<Responsibility> findByRoleId(String roleId);

  Responsibility findByUserIdAndRoleId(String userId, String roleId);

}
