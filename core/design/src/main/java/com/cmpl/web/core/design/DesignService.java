package com.cmpl.web.core.design;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface DesignService extends BaseService<DesignDTO> {

  List<DesignDTO> findByWebsiteId(Long websiteId);

  List<DesignDTO> findByStyleId(Long styleId);

  DesignDTO findByWebsiteIdAndStyleId(Long websiteId, Long styleId);

}
