package com.cmpl.web.core.website;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Website;

public class RenderingWebsiteMapper extends BaseMapper<RenderingWebsiteDTO, Website> {

  @Override
  public RenderingWebsiteDTO toDTO(Website entity) {
    if (entity == null) {
      return null;
    }
    RenderingWebsiteDTO dto = RenderingWebsiteDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Website toEntity(RenderingWebsiteDTO dto) {
    return null;
  }
}
