package com.cmpl.web.core.user.validation;

import com.cmpl.web.core.user.ChangePasswordForm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements
    ConstraintValidator<PasswordConfirmation, ChangePasswordForm> {

  @Override
  public boolean isValid(ChangePasswordForm value, ConstraintValidatorContext context) {

    if (!value.getPassword().equals(value.getConfirmation())) {
      return false;
    }

    return true;
  }
}
