package com.cmpl.web.style;

import com.cmpl.web.media.MediaDTOBuilder;

public class StyleTranslatorImpl implements StyleTranslator {

  @Override
  public StyleDTO fromUpdateFormToDTO(StyleForm form) {
    return StyleDTOBuilder.create().content(form.getContent())
        .media(MediaDTOBuilder.create().name(form.getMediaName()).id(form.getMediaId()).build()).id(form.getId())
        .build();
  }

  @Override
  public StyleResponse fromDTOToResponse(StyleDTO dto) {
    return StyleResponseBuilder.create().style(dto).build();
  }

}