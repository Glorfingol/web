package com.cmpl.web.core.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class GroupValidatorImpl extends BaseValidator implements GroupValidator {

  public GroupValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(GroupCreateForm form, Locale locale) {
    return validate(form.getName(), form.getDescription(), locale);
  }

  @Override
  public Error validateUpdate(GroupUpdateForm form, Locale locale) {
    return validate(form.getName(), form.getDescription(), locale);
  }

  Error validate(String name, String description, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_GROUP_NAME, locale));
    }
    if (!isStringValid(description)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_GROUP_DESCRIPTION, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
