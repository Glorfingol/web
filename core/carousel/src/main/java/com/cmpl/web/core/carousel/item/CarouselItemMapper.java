package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.CarouselItem;
import java.util.Objects;

public class CarouselItemMapper extends BaseMapper<CarouselItemDTO, CarouselItem> {

  private final MediaService mediaService;

  public CarouselItemMapper(MediaService mediaService) {

    this.mediaService = Objects.requireNonNull(mediaService);

  }

  @Override
  public CarouselItemDTO toDTO(CarouselItem entity) {
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().carouselId(entity.getCarouselId())
      .orderInCarousel(entity.getOrderInCarousel())
      .media(mediaService.getEntity(Long.valueOf(entity.getMediaId()))).build();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  public CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = CarouselItemBuilder.create().carouselId(dto.getCarouselId())
      .orderInCarousel(dto.getOrderInCarousel())
      .mediaId(String.valueOf(dto.getMedia().getId())).build();

    fillObject(entity, dto);

    return entity;
  }
}
