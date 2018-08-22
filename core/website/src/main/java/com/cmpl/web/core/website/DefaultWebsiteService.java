package com.cmpl.web.core.website;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Website;

public class DefaultWebsiteService extends DefaultBaseService<WebsiteDTO, Website> implements
    WebsiteService {

  private final WebsiteDAO websiteDAO;

  public DefaultWebsiteService(WebsiteDAO dao, WebsiteMapper mapper) {
    super(dao, mapper);
    this.websiteDAO = dao;
  }

  @Override
  public WebsiteDTO getWebsiteByName(String websiteName) {
    return mapper.toDTO(websiteDAO.getWebsiteByName(websiteName));
  }
}
