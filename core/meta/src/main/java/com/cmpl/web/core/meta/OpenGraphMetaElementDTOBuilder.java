package com.cmpl.web.core.meta;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class OpenGraphMetaElementDTOBuilder extends BaseBuilder<OpenGraphMetaElementDTO> {

  private String pageId;
  private String property;
  private String content;

  private OpenGraphMetaElementDTOBuilder() {

  }

  public OpenGraphMetaElementDTOBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public OpenGraphMetaElementDTOBuilder property(String property) {
    this.property = property;
    return this;
  }

  public OpenGraphMetaElementDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public OpenGraphMetaElementDTO build() {
    OpenGraphMetaElementDTO dto = new OpenGraphMetaElementDTO();
    dto.setContent(content);
    dto.setId(id);
    dto.setCreationDate(creationDate);
    dto.setModificationDate(modificationDate);
    dto.setProperty(property);
    dto.setPageId(pageId);
    return dto;
  }

  public static OpenGraphMetaElementDTOBuilder create() {
    return new OpenGraphMetaElementDTOBuilder();
  }

}
