package cmpl.web.carousel;

import java.util.List;

import cmpl.web.core.service.BaseService;

public interface CarouselService extends BaseService<CarouselDTO> {

  List<CarouselDTO> findByPageId(String pageId);
}
