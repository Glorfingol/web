package com.cmpl.web.carousel;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;

public class CarouselItemResponseBuilder extends Builder<CarouselItemResponse> {

  private CarouselItemDTO carouselItem;
  private Error error;

  public CarouselItemResponseBuilder item(CarouselItemDTO carouselItem) {
    this.carouselItem = carouselItem;
    return this;
  }

  public CarouselItemResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public CarouselItemResponse build() {
    CarouselItemResponse response = new CarouselItemResponse();
    response.setItem(carouselItem);
    response.setError(error);
    return response;
  }
}
