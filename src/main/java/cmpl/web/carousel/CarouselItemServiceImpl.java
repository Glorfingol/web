package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.service.BaseServiceImpl;

public class CarouselItemServiceImpl extends BaseServiceImpl<CarouselItemDTO, CarouselItem> implements
    CarouselItemService {

  private final CarouselItemRepository carouselItemRepository;

  public CarouselItemServiceImpl(CarouselItemRepository carouselItemRepository) {
    super(carouselItemRepository);
    this.carouselItemRepository = carouselItemRepository;
  }

  @Override
  public List<CarouselItemDTO> getByCarouselId(String carouselId) {
    return toListDTO(carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(carouselId));
  }

  @Override
  protected CarouselItemDTO toDTO(CarouselItem entity) {
    CarouselItemDTO dto = new CarouselItemDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = new CarouselItem();
    fillObject(entity, dto);
    return entity;
  }

}
