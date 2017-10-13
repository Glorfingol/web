package cmpl.web.carousel;

import cmpl.web.core.model.BaseResponse;

public class CarouselResponse extends BaseResponse {

  private CarouselDTO carousel;

  public CarouselDTO getCarousel() {
    return carousel;
  }

  public void setCarousel(CarouselDTO carousel) {
    this.carousel = carousel;
  }

}
