package com.cmpl.web.carousel;

import java.util.List;

import com.cmpl.web.core.service.BaseServiceImpl;

public class CarouselServiceImpl extends BaseServiceImpl<CarouselDTO, Carousel> implements CarouselService {

  private final CarouselRepository carouselRepository;
  private final CarouselItemService carouselItemService;

  public CarouselServiceImpl(CarouselRepository carouselRepository, CarouselItemService carouselItemService) {
    super(carouselRepository);
    this.carouselRepository = carouselRepository;
    this.carouselItemService = carouselItemService;
  }

  @Override
  public List<CarouselDTO> findByPageId(String pageId) {
    return toListDTO(carouselRepository.findByPageId(pageId));
  }

  @Override
  protected CarouselDTO toDTO(Carousel entity) {
    CarouselDTO dto = new CarouselDTOBuilder().carouselItems(
        carouselItemService.getByCarouselId(String.valueOf(entity.getId()))).build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Carousel toEntity(CarouselDTO dto) {
    Carousel carousel = new CarouselBuilder().build();
    fillObject(dto, carousel);
    return carousel;
  }

}
