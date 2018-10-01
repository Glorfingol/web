package com.cmpl.web.core.website;

import com.cmpl.web.core.common.service.ReadOnlyService;

public interface RenderingWebsiteService extends ReadOnlyService<RenderingWebsiteDTO> {

  /**
   * Trouver un site via son nom
   */
  RenderingWebsiteDTO getWebsiteByName(String websiteName);

}
