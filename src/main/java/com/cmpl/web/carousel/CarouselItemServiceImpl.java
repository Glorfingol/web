package com.cmpl.web.carousel;

import java.util.List;

import com.cmpl.web.core.service.BaseServiceImpl;
import com.cmpl.web.media.MediaDTO;
import com.cmpl.web.media.MediaService;

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

  @Override
  public CarouselItemDTO createEntity(CarouselItemDTO dto) {

    CarouselItem carouselItem = new CarouselItem();
    fillObject(dto, carouselItem);
    carouselItem.setMediaId(String.valueOf(dto.getMedia().getId()));

    return toDTO(carouselItemRepository.save(carouselItem));
  }
}
