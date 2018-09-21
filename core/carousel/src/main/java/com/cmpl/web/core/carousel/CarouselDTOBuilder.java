package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.common.builder.BaseBuilder;
import java.util.List;

public class CarouselDTOBuilder extends BaseBuilder<CarouselDTO> {

  private String name;

  private List<CarouselItemDTO> carouselItems;

  private CarouselDTOBuilder() {

  }

  public CarouselDTOBuilder name(String name) {
    this.name = name;
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
    dto.setCreationUser(creationUser);
    dto.setModificationUser(modificationUser);
    dto.setId(id);
    dto.setModificationDate(modificationDate);
    dto.setName(name);
    return dto;
  }

  public static CarouselDTOBuilder create() {
    return new CarouselDTOBuilder();
  }

}
