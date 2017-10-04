package cmpl.web.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.page.PAGE_ERROR;

public class MetaElementValidatorImpl implements MetaElementValidator {

  private final WebMessageSourceImpl messageSource;

  public MetaElementValidatorImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public Error validateCreate(String pageId, MetaElementCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!isStringValid(pageId)) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_PAGE_ID, locale));
    }
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_NAME, locale));
    }
    if (!isStringValid(form.getContent())) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_CONTENT, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateCreate(String pageId, OpenGraphMetaElementCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!isStringValid(pageId)) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_PAGE_ID, locale));
    }
    if (!isStringValid(form.getProperty())) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_NAME, locale));
    }
    if (!isStringValid(form.getContent())) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_CONTENT, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  ErrorCause computeCause(META_ELEMENT_ERROR_CAUSE errorCause, Locale locale) {
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
  public Error validateDelete(String id, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(id)) {
      causes.add(computeCause(META_ELEMENT_ERROR_CAUSE.EMPTY_CONTENT, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
