package com.cmpl.web.carousel;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;

public class CarouselResponseBuilder extends Builder<CarouselResponse> {

  private CarouselDTO carousel;
  private Error error;

  private CarouselResponseBuilder() {

  }

  public CarouselResponseBuilder carousel(CarouselDTO carousel) {
    this.carousel = carousel;
    return this;
  }

  public CarouselResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public CarouselResponse build() {
    CarouselResponse response = new CarouselResponse();
    response.setCarousel(carousel);
    response.setError(error);
    return response;
  }

  public static CarouselResponseBuilder create() {
    return new CarouselResponseBuilder();
  }

}
