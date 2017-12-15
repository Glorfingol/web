package com.cmpl.web.core.validator;

import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.error.ERROR;
import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.message.WebMessageSourceImpl;

public class BaseValidator {

  private final WebMessageSourceImpl messageSource;

  public BaseValidator(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  public boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  public ErrorCause computeCause(ERROR_CAUSE errorCause, Locale locale) {
    ErrorCause cause = new ErrorCause();
    cause.setCode(errorCause.toString());
    cause.setMessage(getI18n(errorCause.getCauseKey(), locale));
    return cause;
  }

  public Error computeError(List<ErrorCause> causes) {
    Error error = new Error();
    error.setCode(ERROR.INVALID_REQUEST.toString());
    error.setCauses(causes);
    return error;
  }

  protected String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
