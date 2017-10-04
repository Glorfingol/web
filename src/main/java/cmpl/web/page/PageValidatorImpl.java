package cmpl.web.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.message.WebMessageSourceImpl;

public class PageValidatorImpl implements PageValidator {

  private final WebMessageSourceImpl messageSource;

  public PageValidatorImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public Error validateCreate(PageCreateForm form, Locale locale) {

    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(PAGE_ERROR_CAUSE.EMPTY_NAME, locale));
    }
    if (!isStringValid(form.getMenuTitle())) {
      causes.add(computeCause(PAGE_ERROR_CAUSE.EMPTY_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  ErrorCause computeCause(PAGE_ERROR_CAUSE errorCause, Locale locale) {
    ErrorCause cause = new ErrorCause();
    cause.setCode(errorCause.toString());
    cause.setMessage(getI18n(errorCause.getCauseKey(), locale));
    return cause;
  }

  Error computeError(List<ErrorCause> causes) {
    Error error = new Error();
    error.setCode(PAGE_ERROR.INVALID_REQUEST.toString());
    error.setCauses(causes);
    return error;
  }

  String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

  @Override
  public Error validateUpdate(PageUpdateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(PAGE_ERROR_CAUSE.EMPTY_NAME, locale));
    }
    if (!isStringValid(form.getMenuTitle())) {
      causes.add(computeCause(PAGE_ERROR_CAUSE.EMPTY_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
