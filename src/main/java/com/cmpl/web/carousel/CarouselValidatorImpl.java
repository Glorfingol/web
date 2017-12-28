package com.cmpl.web.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.core.validator.BaseValidator;
import com.cmpl.web.message.WebMessageSource;

public class CarouselValidatorImpl extends BaseValidator implements CarouselValidator {

  public CarouselValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(CarouselCreateForm form, Locale locale) {
    return validateCarousel(form.getName(), form.getPageId(), locale);
  }

  @Override
  public Error validateUpdate(CarouselUpdateForm form, Locale locale) {
    return validateCarousel(form.getName(), form.getPageId(), locale);
  }

  Error validateCarousel(String name, String pageId, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_NAME, locale));
    }
    if (!isStringValid(pageId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_PAGE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  @Override
  public Error validateCreate(CarouselItemCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getCarouselId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_ID, locale));
    }
    if (!isStringValid(form.getMediaId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_MEDIA_ID, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  @Override
  public Error validateDelete(String carouselItemId, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(carouselItemId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_ITEM_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

}
