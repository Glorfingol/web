package com.cmpl.web.core.common.error;

import com.cmpl.web.core.common.builder.Builder;

public class ErrorCauseBuilder extends Builder<ErrorCause> {

  private String code;
  private String message;

  private ErrorCauseBuilder() {

  }

  public ErrorCauseBuilder code(String code) {
    this.code = code;
    return this;
  }

  public ErrorCauseBuilder message(String message) {
    this.message = message;
    return this;
  }

  @Override
  public ErrorCause build() {
    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(code);
    errorCause.setMessage(message);
    return errorCause;
  }

  public static ErrorCauseBuilder create() {
    return new ErrorCauseBuilder();
  }

}
