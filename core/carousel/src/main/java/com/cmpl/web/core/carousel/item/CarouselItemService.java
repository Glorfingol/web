package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

public interface CarouselItemService extends BaseService<CarouselItemDTO> {

  List<CarouselItemDTO> getByCarouselId(String carouselId);

  void deleteEntityInCarousel(String carouselId, Long id);

}
