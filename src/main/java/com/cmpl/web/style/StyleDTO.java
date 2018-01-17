package com.cmpl.web.style;

import com.cmpl.web.core.model.BaseDTO;
import com.cmpl.web.media.MediaDTO;

public class StyleDTO extends BaseDTO {

  private String content;
  private MediaDTO media;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public MediaDTO getMedia() {
    return media;
  }

  public void setMedia(MediaDTO media) {
    this.media = media;
  }

}