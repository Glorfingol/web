package com.cmpl.web.core.style;

public interface StyleTranslator {

  StyleDTO fromUpdateFormToDTO(StyleForm form);

  StyleDTO fromCreateFormToDTO(StyleCreateForm form);

  StyleResponse fromDTOToResponse(StyleDTO dto);
}
