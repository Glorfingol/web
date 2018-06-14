package com.cmpl.web.core.style;

import java.util.Locale;
import java.util.Objects;

public class StyleDispatcherImpl implements StyleDispatcher {

  private final StyleService styleService;
  private final StyleTranslator translator;

  public StyleDispatcherImpl(StyleService styleService, StyleTranslator translator) {
    Objects.requireNonNull(styleService);
    Objects.requireNonNull(translator);
    this.styleService = styleService;
    this.translator = translator;
  }

  @Override
  public StyleResponse updateEntity(StyleForm form, Locale locale) {
    StyleDTO dto = translator.fromUpdateFormToDTO(form);
    StyleDTO updatedDTO = styleService.updateEntity(dto);
    return translator.fromDTOToResponse(updatedDTO);
  }

}
