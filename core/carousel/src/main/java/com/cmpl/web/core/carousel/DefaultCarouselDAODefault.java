package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.models.QCarousel;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultCarouselDAODefault extends DefaultBaseDAO<Carousel> implements CarouselDAO {

  public DefaultCarouselDAODefault(CarouselRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(Carousel.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QCarousel qCarousel = QCarousel.carousel;
    return qCarousel.name.containsIgnoreCase(query);
  }
}
