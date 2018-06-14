package com.cmpl.web.core.page;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface PageDAO extends BaseDAO<Page> {

  Page getPageByName(String pageName);

  List<Page> getPages(Sort sort);
}
