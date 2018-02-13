package com.cmpl.web.core.carousel;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselUpdateFormBuilder extends Builder<CarouselUpdateForm> {

  private String name;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;

  private CarouselUpdateFormBuilder() {

  }

  public CarouselUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public CarouselUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public CarouselUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  @Override
  public CarouselUpdateForm build() {
    CarouselUpdateForm form = new CarouselUpdateForm();
    form.setCreationDate(creationDate);
    form.setId(id);
    form.setModificationDate(modificationDate);
    form.setName(name);
    return form;
  }

  public static CarouselUpdateFormBuilder create() {
    return new CarouselUpdateFormBuilder();
  }

}
