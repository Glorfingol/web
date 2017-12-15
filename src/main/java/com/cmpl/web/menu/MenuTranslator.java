package com.cmpl.web.menu;


public interface MenuTranslator {

  MenuDTO fromCreateFormToDTO(MenuCreateForm form);

  MenuResponse fromDTOToResponse(MenuDTO dto);
}
