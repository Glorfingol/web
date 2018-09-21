package com.cmpl.web.core.page;

public class DefaultPageTranslator implements PageTranslator {

  @Override
  public PageDTO fromCreateFormToDTO(PageCreateForm form) {
    return PageDTOBuilder.create().href(form.getHref()).menuTitle(form.getMenuTitle())
      .name(form.getName()).indexed(form.isIndexed()).href(form.getHref())
      .footer(form.getFooter())
      .meta(form.getMeta()).header(form.getHeader()).body(form.getBody()).amp("").build();
  }

  @Override
  public PageResponse fromDTOToResponse(PageDTO dto) {
    return PageResponseBuilder.create().page(dto).build();
  }

}
