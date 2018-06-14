package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.mapper.BaseMapper;

public class MenuMapper extends BaseMapper<MenuDTO, Menu> {

  @Override
  public MenuDTO toDTO(Menu entity) {
    MenuDTO dto = MenuDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Menu toEntity(MenuDTO dto) {
    Menu entity = MenuBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
