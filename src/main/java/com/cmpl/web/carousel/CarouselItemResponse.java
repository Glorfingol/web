package com.cmpl.web.carousel;

import com.cmpl.web.core.model.BaseResponse;

public class CarouselItemResponse extends BaseResponse {

  private CarouselItemDTO item;

  public CarouselItemDTO getItem() {
    return item;
  }

  public void setItem(CarouselItemDTO item) {
    this.item = item;
  }

}
