package com.cmpl.web.core.validator;

import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.error.ERROR;
import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.error.ErrorBuilder;
import com.cmpl.web.core.error.ErrorCauseBuilder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.message.WebMessageSource;

public class BaseValidator {

  private final WebMessageSource messageSource;

  public BaseValidator(WebMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  public ErrorCause computeCause(ERROR_CAUSE errorCause, Locale locale) {
    return new ErrorCauseBuilder().code(errorCause.toString()).message(getI18n(errorCause.getCauseKey(), locale))
        .build();
  }

  public Error computeError(List<ErrorCause> causes) {
    return new ErrorBuilder().code(ERROR.INVALID_REQUEST.toString()).causes(causes).build();
  }

  protected String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
