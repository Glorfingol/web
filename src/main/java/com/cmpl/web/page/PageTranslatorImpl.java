package com.cmpl.web.page;

public class PageTranslatorImpl implements PageTranslator {

  @Override
  public PageDTO fromCreateFormToDTO(PageCreateForm form) {
    PageDTO dto = new PageDTO();
    dto.setMenuTitle(form.getMenuTitle());
    dto.setName(form.getName());
    dto.setWithNews(form.isWithNews());
    dto.setBody(form.getBody());
    return dto;
  }

  @Override
  public PageResponse fromDTOToResponse(PageDTO dto) {
    PageResponse response = new PageResponse();
    response.setPage(dto);
    return response;
  }

}
