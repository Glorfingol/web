package com.cmpl.web.core.carousel;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;

public class CarouselDAOImpl extends BaseDAOImpl<Carousel> implements CarouselDAO {

  public CarouselDAOImpl(CarouselRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Carousel.class, entityRepository, publisher);
  }
}