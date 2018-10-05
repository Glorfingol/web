package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.Carousel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public class DefaultCarouselService extends
  DefaultBaseService<CarouselDTO, Carousel> implements
  CarouselService {

  public DefaultCarouselService(CarouselDAO carouselDAO, CarouselMapper carouselMapper) {
    super(carouselDAO, carouselMapper);
  }

  @Override
  public CarouselDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  public Page<CarouselDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  public CarouselDTO createEntity(CarouselDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
