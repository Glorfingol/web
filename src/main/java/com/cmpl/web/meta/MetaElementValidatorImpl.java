package com.cmpl.web.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.error.ERROR_CAUSE;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;
import com.cmpl.web.core.validator.BaseValidator;
import com.cmpl.web.message.WebMessageSource;

public class MetaElementValidatorImpl extends BaseValidator implements MetaElementValidator {

  public MetaElementValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(String pageId, MetaElementCreateForm form, Locale locale) {
    return validateCreate(pageId, form.getName(), form.getContent(), locale);
  }

  @Override
  public Error validateCreate(String pageId, OpenGraphMetaElementCreateForm form, Locale locale) {
    return validateCreate(pageId, form.getProperty(), form.getContent(), locale);
  }

  Error validateCreate(String pageId, String name, String content, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(pageId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_PAGE_ID, locale));
    }
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_META_NAME, locale));
    }
    if (!isStringValid(content)) {
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
