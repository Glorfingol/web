package com.cmpl.web.carousel;

import com.cmpl.web.core.model.BaseDTO;
import com.cmpl.web.media.MediaDTO;

public class CarouselItemDTO extends BaseDTO {

  private MediaDTO media;
  private String carouselId;
  private int orderInCarousel;

  public MediaDTO getMedia() {
    return media;
  }

  public void setMedia(MediaDTO media) {
    this.media = media;
  }

  public String getCarouselId() {
    return carouselId;
  }

  public void setCarouselId(String carouselId) {
    this.carouselId = carouselId;
  }

  public int getOrderInCarousel() {
    return orderInCarousel;
  }

  public void setOrderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
  }

}
