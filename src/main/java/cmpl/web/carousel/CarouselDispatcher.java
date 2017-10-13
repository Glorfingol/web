package cmpl.web.carousel;

import java.util.Locale;

public interface CarouselDispatcher {

  CarouselResponse createEntity(CarouselCreateForm form, Locale locale);

  CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale);

  CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale);

}
