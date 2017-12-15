package com.cmpl.web.style;

public interface StyleTranslator {

  StyleDTO fromUpdateFormToDTO(StyleForm form);

  StyleResponse fromDTOToResponse(StyleDTO dto);
}
