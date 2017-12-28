package com.cmpl.web.menu;

public class MenuTranslatorImpl implements MenuTranslator {

  @Override
  public MenuDTO fromCreateFormToDTO(MenuCreateForm form) {
    return new MenuDTOBuilder().href(form.getHref()).pageId(form.getPageId()).parentId(form.getParentId())
        .label(form.getLabel()).title(form.getTitle()).orderInMenu(form.getOrderInMenu()).build();
  }

  @Override
  public MenuResponse fromDTOToResponse(MenuDTO dto) {
    return new MenuResponseBuilder().menu(dto).build();
  }

}
