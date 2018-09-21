package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.FieldError;

public class ChangePasswordResponseBuilder extends Builder<ChangePasswordResponse> {

  private List<FieldError> errors;

  private ChangePasswordResponseBuilder() {
    errors = new ArrayList<>();
  }

  public ChangePasswordResponseBuilder errors(List<FieldError> errors) {
    this.errors.addAll(errors);
    return this;
  }

  @Override
  public ChangePasswordResponse build() {
    ChangePasswordResponse response = new ChangePasswordResponse();
    response.setErrors(errors);
    return response;
  }

  public static ChangePasswordResponseBuilder create() {
    return new ChangePasswordResponseBuilder();
  }
}
