package com.cmpl.web.style;

import com.cmpl.web.core.model.BaseResponse;

public class StyleResponse extends BaseResponse {

  private StyleDTO style;

  public StyleDTO getStyle() {
    return style;
  }

  public void setStyle(StyleDTO style) {
    this.style = style;
  }

}
