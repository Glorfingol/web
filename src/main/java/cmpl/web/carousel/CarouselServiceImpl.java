package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.service.BaseServiceImpl;

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
    CarouselDTO dto = new CarouselDTO();
    fillObject(entity, dto);
    List<CarouselItemDTO> carouselItems = carouselItemService.getByCarouselId(String.valueOf(entity.getId()));
    dto.setCarouselItems(carouselItems);
    return dto;
  }

  @Override
  protected Carousel toEntity(CarouselDTO dto) {
    Carousel carousel = new Carousel();
    fillObject(carousel, dto);
    return carousel;
  }

}
