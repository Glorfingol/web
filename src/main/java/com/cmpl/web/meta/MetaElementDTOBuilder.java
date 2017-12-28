package com.cmpl.web.meta;

import com.cmpl.web.core.builder.BaseBuilder;

public class MetaElementDTOBuilder extends BaseBuilder<MetaElementDTO> {

  private String pageId;
  private String name;
  private String content;

  public MetaElementDTOBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public MetaElementDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetaElementDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public MetaElementDTO build() {
    MetaElementDTO dto = new MetaElementDTO();
    dto.setContent(content);
    dto.setId(id);
    dto.setCreationDate(creationDate);
    dto.setModificationDate(modificationDate);
    dto.setName(name);
    dto.setPageId(pageId);
    return dto;
  }

}
