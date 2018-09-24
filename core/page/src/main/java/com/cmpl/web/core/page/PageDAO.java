package com.cmpl.web.core.page;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PageDAO extends BaseDAO<Page> {

    Page getPageByName(String pageName);

    List<Page> getPages(Sort sort);
}
