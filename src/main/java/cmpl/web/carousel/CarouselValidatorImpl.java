package cmpl.web.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import cmpl.web.core.error.ERROR_CAUSE;
import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.core.validator.BaseValidator;
import cmpl.web.message.WebMessageSourceImpl;

public class CarouselValidatorImpl extends BaseValidator implements CarouselValidator {

  public CarouselValidatorImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(CarouselCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_NAME, locale));
    }
    if (!isStringValid(form.getPageId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_PAGE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateUpdate(CarouselUpdateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_NAME, locale));
    }
    if (!isStringValid(form.getPageId())) {
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

}
