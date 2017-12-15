package com.cmpl.web.page;

public interface PageTranslator {

  PageDTO fromCreateFormToDTO(PageCreateForm form);

  PageResponse fromDTOToResponse(PageDTO dto);

}
