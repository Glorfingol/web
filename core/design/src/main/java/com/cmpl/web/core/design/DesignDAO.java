package com.cmpl.web.core.design;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Design;
import java.util.List;

public interface DesignDAO extends BaseDAO<Design> {

  List<Design> findByWebsiteId(Long websiteId);

  List<Design> findByStyleId(Long styleId);

  Design findByWebsiteIdAndStyleId(Long websiteId, Long styleId);

}
