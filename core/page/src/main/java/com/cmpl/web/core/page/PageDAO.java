package com.cmpl.web.core.page;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Page;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface PageDAO extends BaseDAO<Page> {

  Page getPageByName(String pageName);

  List<Page> getPagesByHref(String href);

  List<Page> getPages(Sort sort);
}
