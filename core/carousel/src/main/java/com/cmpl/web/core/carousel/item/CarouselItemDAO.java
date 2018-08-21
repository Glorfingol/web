package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.CarouselItem;
import java.util.List;

public interface CarouselItemDAO extends BaseDAO<CarouselItem> {

  List<CarouselItem> getByCarouselId(String carouselId);

}
