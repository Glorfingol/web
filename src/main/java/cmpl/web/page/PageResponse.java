package cmpl.web.page;

import cmpl.web.core.model.BaseResponse;

public class PageResponse extends BaseResponse {

  private PageDTO page;

  public PageDTO getPage() {
    return page;
  }

  public void setPage(PageDTO page) {
    this.page = page;
  }

}
