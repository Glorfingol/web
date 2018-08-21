package com.cmpl.web.core.design;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Design;
import java.util.List;

public class DesignServiceImpl extends BaseServiceImpl<DesignDTO, Design> implements DesignService {

  private final DesignDAO designDAO;

  public DesignServiceImpl(DesignDAO designDAO, DesignMapper designMapper) {
    super(designDAO, designMapper);
    this.designDAO = designDAO;
  }

  @Override
  public List<DesignDTO> findByWebsiteId(Long websiteId) {
    return mapper.toListDTO(designDAO.findByWebsiteId(websiteId));
  }

  @Override
  public List<DesignDTO> findByStyleId(Long styleId) {
    return mapper.toListDTO(designDAO.findByStyleId(styleId));
  }

  @Override
  public DesignDTO findByWebsiteIdAndStyleId(Long websiteId, Long styleId) {
    return mapper.toDTO(designDAO.findByWebsiteIdAndStyleId(websiteId, styleId));
  }

}
