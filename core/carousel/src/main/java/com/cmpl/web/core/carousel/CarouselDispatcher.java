package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;
import com.cmpl.web.core.common.exception.BaseException;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CarouselDispatcher {

  CarouselResponse createEntity(CarouselCreateForm form, Locale locale);

  CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale);

  CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale);

  CarouselResponse deleteEntity(String carouselId, Locale locale);

  void deleteCarouselItemEntity(String carouselId, String carouselItemId, Locale locale)
    throws BaseException;

  Page<CarouselDTO> searchEntities(PageRequest pageRequest, String query);

}
