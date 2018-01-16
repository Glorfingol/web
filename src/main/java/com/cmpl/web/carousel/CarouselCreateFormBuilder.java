package com.cmpl.web.carousel;

import com.cmpl.web.core.builder.Builder;

public class CarouselCreateFormBuilder extends Builder<CarouselCreateForm> {

  private String name;
  private String pageId;

  private CarouselCreateFormBuilder() {

  }

  public CarouselCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public CarouselCreateForm build() {
    CarouselCreateForm form = new CarouselCreateForm();
    form.setName(name);
    form.setPageId(pageId);
    return form;
  }

  public static CarouselCreateFormBuilder create() {
    return new CarouselCreateFormBuilder();
  }

}
