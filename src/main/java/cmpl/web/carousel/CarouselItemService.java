package cmpl.web.carousel;

import java.util.List;

public interface CarouselItemService {

  List<CarouselItemDTO> getByCarouselId(String carouselId);

}
