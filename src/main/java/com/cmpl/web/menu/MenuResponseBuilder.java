package com.cmpl.web.menu;

import com.cmpl.web.core.builder.Builder;
import com.cmpl.web.core.model.Error;

public class MenuResponseBuilder extends Builder<MenuResponse> {

  private MenuDTO menu;
  private Error error;

  public MenuResponseBuilder menu(MenuDTO menu) {
    this.menu = menu;
    return this;
  }

  public MenuResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public MenuResponse build() {
    MenuResponse response = new MenuResponse();
    response.setMenu(menu);
    response.setError(error);
    return response;
  }

}
