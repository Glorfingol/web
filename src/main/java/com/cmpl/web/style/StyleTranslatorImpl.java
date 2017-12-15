package com.cmpl.web.style;

import com.cmpl.web.media.MediaDTO;

public class StyleTranslatorImpl implements StyleTranslator {

  @Override
  public StyleDTO fromUpdateFormToDTO(StyleForm form) {
    StyleDTO style = new StyleDTO();
    style.setId(form.getId());
    style.setContent(form.getContent());

    MediaDTO media = new MediaDTO();
    media.setId(form.getMediaId());
    media.setName(form.getMediaName());
    style.setMedia(media);

    return style;
  }

  @Override
  public StyleResponse fromDTOToResponse(StyleDTO dto) {
    StyleResponse response = new StyleResponse();
    response.setStyle(dto);
    return response;
  }

}
