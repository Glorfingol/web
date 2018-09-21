package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.FieldError;

public class RequestPasswordLinkResponseBuilder extends Builder<RequestPasswordLinkResponse> {

  private List<FieldError> errors;


  private RequestPasswordLinkResponseBuilder() {
    errors = new ArrayList<>();
  }

  public RequestPasswordLinkResponseBuilder errors(List<FieldError> errors) {
    this.errors.addAll(errors);
    return this;
  }


  @Override
  public RequestPasswordLinkResponse build() {
    RequestPasswordLinkResponse requestPasswordLinkResponse = new RequestPasswordLinkResponse();
    requestPasswordLinkResponse.setErrors(errors);
    return requestPasswordLinkResponse;
  }

  public static RequestPasswordLinkResponseBuilder create() {
    return new RequestPasswordLinkResponseBuilder();
  }
}
