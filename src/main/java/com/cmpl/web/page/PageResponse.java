package com.cmpl.web.page;

import com.cmpl.web.core.model.BaseResponse;

public class PageResponse extends BaseResponse {

  private PageDTO page;

  public PageDTO getPage() {
    return page;
  }

  public void setPage(PageDTO page) {
    this.page = page;
  }

}
