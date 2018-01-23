package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class CarouselBuilder extends BaseBuilder<Carousel> {

  private String name;
  private String pageId;

  private CarouselBuilder() {

  }

  public CarouselBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public Carousel build() {
    Carousel carousel = new Carousel();
    carousel.setCreationDate(creationDate);
    carousel.setId(id);
    carousel.setModificationDate(modificationDate);
    carousel.setName(name);
    carousel.setPageId(pageId);
    return carousel;
  }

  public static CarouselBuilder create() {
    return new CarouselBuilder();
  }

}
