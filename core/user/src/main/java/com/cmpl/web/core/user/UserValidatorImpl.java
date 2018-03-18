package com.cmpl.web.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class UserValidatorImpl extends BaseValidator implements UserValidator {

  public UserValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(UserCreateForm form, Locale locale) {
    return null;
  }

  @Override
  public Error validateUpdate(UserUpdateForm form, Locale locale) {
    return null;
  }

  Error validate(String login, String email, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(login)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_LOGIN, locale));
    }
    if (!isStringValid(email)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_EMAIL, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
