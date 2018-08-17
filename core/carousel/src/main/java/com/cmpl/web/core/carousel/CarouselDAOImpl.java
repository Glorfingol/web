package com.cmpl.web.core.carousel;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.models.QCarousel;
import com.querydsl.core.types.Predicate;

public class CarouselDAOImpl extends BaseDAOImpl<Carousel> implements CarouselDAO {

  public CarouselDAOImpl(CarouselRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Carousel.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QCarousel qCarousel = QCarousel.carousel;
    return qCarousel.name.containsIgnoreCase(query);
  }
}
