package com.cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cmpl.web.core.service.BaseServiceImpl;

/**
 * Service du menu
 * 
 * @author Louis
 *
 */
public class MenuServiceImpl extends BaseServiceImpl<MenuDTO, Menu> implements MenuService {

  private final MenuRepository menuRepository;

  public MenuServiceImpl(MenuRepository menuRepository) {
    super(menuRepository);
    this.menuRepository = menuRepository;
  }

  @Override
  protected MenuDTO toDTO(Menu entity) {
    MenuDTO dto = new MenuDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Menu toEntity(MenuDTO dto) {
    Menu entity = new Menu();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public List<MenuDTO> getMenus() {
    return toListDTO(menuRepository.findAll(new Sort(Direction.ASC, "orderInMenu")));
  }

  @Override
  public List<MenuDTO> toListDTO(List<Menu> entities) {
    return computeMenus(entities);
  }

  MenuDTO computeMenuDTOToReturn(Menu menu) {
    MenuDTO menuDTO = toDTO(menu);

    List<Menu> children = menuRepository.findByParentId(String.valueOf(menu.getId()));
    menuDTO.setChildren(computeMenus(children));

    return menuDTO;
  }

  List<MenuDTO> computeMenus(List<Menu> entities) {
    List<MenuDTO> menus = new ArrayList<>();
    entities.forEach(entity -> menus.add(computeMenuDTOToReturn(entity)));
    return menus;
  }

}
