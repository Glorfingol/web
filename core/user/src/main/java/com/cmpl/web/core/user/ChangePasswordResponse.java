package com.cmpl.web.core.user;

import com.cmpl.web.core.common.resource.BaseResponse;
import java.util.List;
import org.springframework.validation.FieldError;

public class ChangePasswordResponse extends BaseResponse {

  private List<FieldError> errors;

  public List<FieldError> getErrors() {
    return errors;
  }

  public void setErrors(List<FieldError> errors) {
    this.errors = errors;
  }
}
