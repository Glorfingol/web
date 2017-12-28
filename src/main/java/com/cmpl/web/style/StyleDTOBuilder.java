package com.cmpl.web.style;

import com.cmpl.web.core.builder.BaseBuilder;
import com.cmpl.web.media.MediaDTO;

public class StyleDTOBuilder extends BaseBuilder<StyleDTO> {

  private String content;
  private MediaDTO media;

  public StyleDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public StyleDTOBuilder media(MediaDTO media) {
    this.media = media;
    return this;
  }

  @Override
  public StyleDTO build() {
    StyleDTO style = new StyleDTO();
    style.setContent(content);
    style.setCreationDate(creationDate);
    style.setId(id);
    style.setMedia(media);
    style.setModificationDate(modificationDate);
    return style;
  }

}
