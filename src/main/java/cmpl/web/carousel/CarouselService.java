package cmpl.web.carousel;

import java.util.List;

public interface CarouselService {

  List<CarouselDTO> findByPageId(String pageId);
}
