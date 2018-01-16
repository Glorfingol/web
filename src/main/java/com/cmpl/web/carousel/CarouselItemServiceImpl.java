package com.cmpl.web.carousel;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;

import com.cmpl.web.core.service.BaseServiceImpl;
import com.cmpl.web.media.MediaService;

@CacheConfig(cacheNames = {"carouselItems"})
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
    CarouselItemDTO dto = CarouselItemDTOBuilder.create()
        .media(mediaService.getEntity(Long.valueOf(entity.getMediaId()))).build();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = CarouselItemBuilder.create().mediaId(String.valueOf(dto.getMedia().getId())).build();

    fillObject(entity, dto);

    return entity;
  }

  @Override
  public CarouselItemDTO createEntity(CarouselItemDTO dto) {

    CarouselItem carouselItem = CarouselItemBuilder.create().mediaId(String.valueOf(dto.getMedia().getId())).build();
    fillObject(dto, carouselItem);

    return toDTO(carouselItemRepository.save(carouselItem));
  }
}
