package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class CarouselServiceImpl extends BaseServiceImpl<CarouselDTO, Carousel> implements CarouselService {

  private final CarouselRepository carouselRepository;
  private final CarouselItemService carouselItemService;

  public CarouselServiceImpl(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    super(carouselRepository);
    this.carouselRepository = carouselRepository;
    this.carouselItemService = carouselItemService;
  }

  @Override
  protected CarouselDTO toDTO(Carousel entity) {
    CarouselDTO dto = CarouselDTOBuilder.create()
        .carouselItems(carouselItemService.getByCarouselId(String.valueOf(entity.getId()))).build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Carousel toEntity(CarouselDTO dto) {
    Carousel carousel = CarouselBuilder.create().build();
    fillObject(dto, carousel);
    return carousel;
  }

}
