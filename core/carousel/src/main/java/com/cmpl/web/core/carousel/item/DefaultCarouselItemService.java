package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.CarouselItem;
import java.util.List;

public class DefaultCarouselItemService extends
  DefaultBaseService<CarouselItemDTO, CarouselItem>
  implements CarouselItemService {

  private final CarouselItemDAO carouselItemDAO;

  public DefaultCarouselItemService(CarouselItemDAO carouselItemDAO,
    CarouselItemMapper carouselItemMapper) {
    super(carouselItemDAO, carouselItemMapper);
    this.carouselItemDAO = carouselItemDAO;
  }

  @Override
  public List<CarouselItemDTO> getByCarouselId(String carouselId) {
    return mapper.toListDTO(carouselItemDAO.getByCarouselId(carouselId));
  }

  @Override
  public CarouselItemDTO createEntity(CarouselItemDTO dto) {
    return mapper.toDTO(carouselItemDAO.createEntity(mapper.toEntity(dto)));
  }

  @Override
  public CarouselItemDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  public void deleteEntityInCarousel(String carouselId, Long id) {
    deleteEntity(id);
  }

}
