package com.cmpl.web.core.error;

import java.util.List;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;
import com.cmpl.web.core.model.ErrorCause;

public class ErrorBuilder extends Builder<Error> {

  private String code;
  private String message;
  private List<ErrorCause> causes;

  public ErrorBuilder code(String code) {
    this.code = code;
    return this;
  }

  public ErrorBuilder message(String message) {
    this.message = message;
    return this;
  }

  public ErrorBuilder causes(List<ErrorCause> causes) {
    this.causes = causes;
    return this;
  }

  @Override
  public Error build() {
    Error error = new Error();
    error.setCauses(causes);
    error.setCode(code);
    error.setMessage(message);
    return error;
  }

}
