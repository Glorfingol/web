package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.service.BaseServiceImpl;
import cmpl.web.media.MediaDTO;
import cmpl.web.media.MediaService;

public class CarouselItemServiceImpl extends BaseServiceImpl<CarouselItemDTO, CarouselItem> implements
    CarouselItemService {

  private final CarouselItemRepository carouselItemRepository;
  private final MediaService mediaService;

  public CarouselItemServiceImpl(CarouselItemRepository carouselItemRepository, MediaService mediaService) {
    super(carouselItemRepository);
    this.carouselItemRepository = carouselItemRepository;
    this.mediaService = mediaService;
  }

  @Override
  public List<CarouselItemDTO> getByCarouselId(String carouselId) {
    return toListDTO(carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(carouselId));
  }

  @Override
  protected CarouselItemDTO toDTO(CarouselItem entity) {
    CarouselItemDTO dto = new CarouselItemDTO();

    fillObject(entity, dto);
    MediaDTO media = mediaService.getEntity(Long.valueOf(entity.getMediaId()));
    dto.setMedia(media);

    return dto;
  }

  @Override
  protected CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = new CarouselItem();

    fillObject(entity, dto);
    entity.setMediaId(String.valueOf(dto.getMedia().getId()));

    return entity;
  }
}
