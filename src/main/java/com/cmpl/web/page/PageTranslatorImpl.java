package com.cmpl.web.page;

public class PageTranslatorImpl implements PageTranslator {

  @Override
  public PageDTO fromCreateFormToDTO(PageCreateForm form) {
    return PageDTOBuilder.create().menuTitle(form.getMenuTitle()).name(form.getName()).withNews(form.isWithNews())
        .footer(form.getFooter()).header(form.getHeader()).body(form.getBody()).build();
  }

  @Override
  public PageResponse fromDTOToResponse(PageDTO dto) {
    return PageResponseBuilder.create().page(dto).build();
  }

}
