package com.cmpl.web.core.carousel.item;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;

public interface CarouselItemDAO extends BaseDAO<CarouselItem> {

  List<CarouselItem> getByCarouselId(String carouselId);

}
