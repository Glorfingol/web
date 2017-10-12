package cmpl.web.menu;

import cmpl.web.core.model.BaseResponse;

public class MenuResponse extends BaseResponse {

  private MenuDTO menu;

  public MenuDTO getMenu() {
    return menu;
  }

  public void setMenu(MenuDTO menu) {
    this.menu = menu;
  }

}
