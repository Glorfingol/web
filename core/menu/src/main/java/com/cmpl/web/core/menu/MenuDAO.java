package com.cmpl.web.core.menu;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface MenuDAO extends BaseDAO<Menu> {

  List<Menu> getMenus(Sort sort);

  List<Menu> findByParentId(String parentId);

}
