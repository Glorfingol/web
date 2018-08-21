package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Menu;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface MenuDAO extends BaseDAO<Menu> {

  List<Menu> getMenus(Sort sort);

  List<Menu> findByParentId(String parentId);

}
