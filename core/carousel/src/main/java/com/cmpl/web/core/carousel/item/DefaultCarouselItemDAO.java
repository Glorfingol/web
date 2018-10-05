package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.CarouselItem;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultCarouselItemDAO extends DefaultBaseDAO<CarouselItem> implements
  CarouselItemDAO {

  private final CarouselItemRepository carouselItemRepository;

  public DefaultCarouselItemDAO(CarouselItemRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(CarouselItem.class, entityRepository, publisher);
    this.carouselItemRepository = entityRepository;
  }

  @Override
  public List<CarouselItem> getByCarouselId(String carouselId) {
    return carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(carouselId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    throw new UnsupportedOperationException();
  }
}
