package cmpl.web.model.news.rest;

import cmpl.web.model.news.error.Error;

public class BaseResponse {

  private Error error;

  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }

}
