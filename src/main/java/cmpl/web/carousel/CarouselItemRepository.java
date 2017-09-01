package cmpl.web.carousel;

import java.util.List;

import org.springframework.stereotype.Repository;

import cmpl.web.core.repository.BaseRepository;

@Repository
public interface CarouselItemRepository extends BaseRepository<CarouselItem> {

  List<CarouselItem> findByCarouselIdOrderByOrderInCarousel(String carouselId);

}
