package com.cmpl.web.core.carousel;

import java.time.LocalDate;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselUpdateFormBuilder extends Builder<CarouselUpdateForm> {

  private String name;
  private String pageId;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  private CarouselUpdateFormBuilder() {

  }

  public CarouselUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselUpdateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public CarouselUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public CarouselUpdateFormBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public CarouselUpdateFormBuilder modificationDate(LocalDate modificationDate) {
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
    form.setPageId(pageId);
    return form;
  }

  public static CarouselUpdateFormBuilder create() {
    return new CarouselUpdateFormBuilder();
  }

}
