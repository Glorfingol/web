package cmpl.web.carousel;

import java.util.Locale;

import cmpl.web.core.model.Error;

public interface CarouselValidator {

  Error validateCreate(CarouselCreateForm form, Locale locale);

  Error validateCreate(CarouselItemCreateForm form, Locale locale);

  Error validateUpdate(CarouselUpdateForm form, Locale locale);

}
