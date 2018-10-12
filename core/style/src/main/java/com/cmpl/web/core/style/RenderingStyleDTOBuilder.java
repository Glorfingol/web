package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RenderingStyleDTOBuilder extends BaseBuilder<RenderingStyleDTO> {

  private String mediaSrc;


  private RenderingStyleDTOBuilder() {

  }


  public RenderingStyleDTOBuilder mediaSrc(String mediaSrc) {
    this.mediaSrc = mediaSrc;
    return this;
  }

  @Override
  public RenderingStyleDTO build() {
    RenderingStyleDTO renderingStyleDTO = new RenderingStyleDTO();
    renderingStyleDTO.setMediaSrc(mediaSrc);
    renderingStyleDTO.setId(id);
    renderingStyleDTO.setCreationDate(creationDate);
    renderingStyleDTO.setModificationDate(modificationDate);
    renderingStyleDTO.setCreationUser(creationUser);
    renderingStyleDTO.setModificationUser(modificationUser);
    return renderingStyleDTO;
  }

  public static RenderingStyleDTOBuilder create() {
    return new RenderingStyleDTOBuilder();
  }
}
