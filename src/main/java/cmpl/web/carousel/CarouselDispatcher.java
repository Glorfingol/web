package cmpl.web.carousel;

import java.util.Locale;

import cmpl.web.core.model.BaseException;

public interface CarouselDispatcher {

  CarouselResponse createEntity(CarouselCreateForm form, Locale locale);

  CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale);

  CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale);

  void deleteCarouselItemEntity(String carouselItemId, Locale locale) throws BaseException;

}
