package cmpl.web.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import cmpl.web.core.error.ERROR_CAUSE;
import cmpl.web.core.model.Error;
import cmpl.web.core.model.ErrorCause;
import cmpl.web.core.validator.BaseValidator;
import cmpl.web.message.WebMessageSourceImpl;

public class MetaElementValidatorImpl extends BaseValidator implements MetaElementValidator {

  public MetaElementValidatorImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(String pageId, MetaElementCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!isStringValid(pageId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_PAGE_ID, locale));
    }
    if (!isStringValid(form.getName())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_NAME, locale));
    }
    if (!isStringValid(form.getContent())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_CONTENT, locale));
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
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_PAGE_ID, locale));
    }
    if (!isStringValid(form.getProperty())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_NAME, locale));
    }
    if (!isStringValid(form.getContent())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_CONTENT, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String id, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(id)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_CONTENT, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
