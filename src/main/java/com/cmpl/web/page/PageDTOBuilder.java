package com.cmpl.web.page;

import java.util.List;

import com.cmpl.web.core.builder.BaseBuilder;
import com.cmpl.web.meta.MetaElementDTO;
import com.cmpl.web.meta.OpenGraphMetaElementDTO;

public class PageDTOBuilder extends BaseBuilder<PageDTO> {

  private String name;
  private String menuTitle;
  private String body;
  private boolean withNews;
  private String header;
  private String footer;

  private List<MetaElementDTO> metaElements;
  private List<OpenGraphMetaElementDTO> openGraphMetaElements;

  private PageDTOBuilder() {

  }

  public PageDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageDTOBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageDTOBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageDTOBuilder withNews(boolean withNews) {
    this.withNews = withNews;
    return this;
  }

  public PageDTOBuilder header(String header) {
    this.header = header;
    return this;
  }

  public PageDTOBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  public PageDTOBuilder metaElements(List<MetaElementDTO> metaElements) {
    this.metaElements = metaElements;
    return this;
  }

  public PageDTOBuilder openGraphMetaElements(List<OpenGraphMetaElementDTO> openGraphMetaElements) {
    this.openGraphMetaElements = openGraphMetaElements;
    return this;
  }

  @Override
  public PageDTO build() {
    PageDTO pageDTO = new PageDTO();
    pageDTO.setBody(body);
    pageDTO.setCreationDate(creationDate);
    pageDTO.setFooter(footer);
    pageDTO.setHeader(header);
    pageDTO.setId(id);
    pageDTO.setMenuTitle(menuTitle);
    pageDTO.setMetaElements(metaElements);
    pageDTO.setModificationDate(modificationDate);
    pageDTO.setName(name);
    pageDTO.setOpenGraphMetaElements(openGraphMetaElements);
    pageDTO.setWithNews(withNews);

    return pageDTO;
  }

  public static PageDTOBuilder create() {
    return new PageDTOBuilder();
  }

}
