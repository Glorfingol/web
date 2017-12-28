package com.cmpl.web.page;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;

public class PageResponseBuilder extends Builder<PageResponse> {

  private PageDTO page;
  private Error error;

  public PageResponseBuilder page(PageDTO page) {
    this.page = page;
    return this;
  }

  public PageResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public PageResponse build() {
    PageResponse response = new PageResponse();
    response.setPage(page);
    response.setError(error);
    return response;
  }

}
