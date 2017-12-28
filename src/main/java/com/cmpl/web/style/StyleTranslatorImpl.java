package com.cmpl.web.style;

import com.cmpl.web.media.MediaDTOBuilder;

public class StyleTranslatorImpl implements StyleTranslator {

  @Override
  public StyleDTO fromUpdateFormToDTO(StyleForm form) {
    return new StyleDTOBuilder().content(form.getContent())
        .media(new MediaDTOBuilder().name(form.getMediaName()).id(form.getMediaId()).build()).id(form.getId()).build();
  }

  @Override
  public StyleResponse fromDTOToResponse(StyleDTO dto) {
    return new StyleResponseBuilder().style(dto).build();
  }

}
