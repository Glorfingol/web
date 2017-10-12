package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.service.BaseService;

public interface CarouselItemService extends BaseService<CarouselItemDTO> {

  List<CarouselItemDTO> getByCarouselId(String carouselId);

}
