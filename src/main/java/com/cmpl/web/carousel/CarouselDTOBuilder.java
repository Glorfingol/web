package com.cmpl.web.carousel;

import java.util.List;

import com.cmpl.web.core.builder.BaseBuilder;

public class CarouselDTOBuilder extends BaseBuilder<CarouselDTO> {

  private String name;
  private String pageId;
  private List<CarouselItemDTO> carouselItems;

  public CarouselDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselDTOBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public CarouselDTOBuilder carouselItems(List<CarouselItemDTO> carouselItems) {
    this.carouselItems = carouselItems;
    return this;
  }

  @Override
  public CarouselDTO build() {
    CarouselDTO dto = new CarouselDTO();
    dto.setCarouselItems(carouselItems);
    dto.setCreationDate(creationDate);
    dto.setId(id);
    dto.setModificationDate(modificationDate);
    dto.setName(name);
    dto.setPageId(pageId);
    return dto;
  }

}
