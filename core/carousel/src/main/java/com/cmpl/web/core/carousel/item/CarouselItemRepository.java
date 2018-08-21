package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.CarouselItem;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselItemRepository extends BaseRepository<CarouselItem> {

  List<CarouselItem> findByCarouselIdOrderByOrderInCarousel(String carouselId);

}
