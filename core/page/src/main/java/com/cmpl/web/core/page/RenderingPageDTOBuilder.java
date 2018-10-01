package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RenderingPageDTOBuilder extends BaseBuilder<RenderingPageDTO> {

  private String name;

  private String menuTitle;

  private String href;

  private boolean indexed;


  private RenderingPageDTOBuilder() {

  }

  public RenderingPageDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RenderingPageDTOBuilder href(String href) {
    this.href = href;
    return this;
  }

  public RenderingPageDTOBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }


  public RenderingPageDTOBuilder indexed(boolean indexed) {
    this.indexed = indexed;
    return this;
  }

  @Override
  public RenderingPageDTO build() {
    RenderingPageDTO pageDTO = new RenderingPageDTO();
    pageDTO.setHref(href);
    pageDTO.setMenuTitle(menuTitle);
    pageDTO.setName(name);
    pageDTO.setIndexed(indexed);
    pageDTO.setCreationDate(creationDate);
    pageDTO.setCreationUser(creationUser);
    pageDTO.setModificationUser(modificationUser);
    pageDTO.setId(id);
    pageDTO.setModificationDate(modificationDate);
    return pageDTO;
  }

  public static RenderingPageDTOBuilder create() {
    return new RenderingPageDTOBuilder();
  }
}
