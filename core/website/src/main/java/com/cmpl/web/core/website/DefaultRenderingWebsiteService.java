package com.cmpl.web.core.website;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.Website;

public class DefaultRenderingWebsiteService extends
  DefaultReadOnlyService<RenderingWebsiteDTO, Website> implements RenderingWebsiteService {

  private final WebsiteDAO websiteDAO;

  public DefaultRenderingWebsiteService(WebsiteDAO dao, RenderingWebsiteMapper mapper) {
    super(dao, mapper);
    this.websiteDAO = dao;
  }

  @Override
  public RenderingWebsiteDTO getWebsiteByName(String websiteName) {
    return mapper.toDTO(websiteDAO.getWebsiteByName(websiteName));
  }
}
