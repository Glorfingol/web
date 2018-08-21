package com.cmpl.web.core.website;

import com.cmpl.web.core.common.service.BaseService;

public interface WebsiteService extends BaseService<WebsiteDTO> {

  /**
   * Trouver un site via son nom
   */
  WebsiteDTO getWebsiteByName(String websiteName);
}
