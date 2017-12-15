package com.cmpl.web.menu;

public class MenuTranslatorImpl implements MenuTranslator {

  @Override
  public MenuDTO fromCreateFormToDTO(MenuCreateForm form) {

    MenuDTO menu = new MenuDTO();
    menu.setHref(form.getHref());
    menu.setPageId(form.getPageId());
    menu.setParentId(form.getParentId());
    menu.setLabel(form.getLabel());
    menu.setTitle(form.getTitle());
    menu.setOrderInMenu(form.getOrderInMenu());

    return menu;
  }

  @Override
  public MenuResponse fromDTOToResponse(MenuDTO dto) {
    MenuResponse response = new MenuResponse();
    response.setMenu(dto);
    return response;
  }

}
