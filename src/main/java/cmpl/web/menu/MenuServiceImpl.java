package cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

import cmpl.web.core.service.BaseServiceImpl;

/**
 * Service du menu
 * 
 * @author Louis
 *
 */
public class MenuServiceImpl extends BaseServiceImpl<MenuDTO, Menu> implements MenuService {

  private final MenuRepository menuRepository;

  private MenuServiceImpl(MenuRepository menuRepository) {
    super(menuRepository);
    this.menuRepository = menuRepository;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param menuRepository
   * @return
   */
  public static MenuServiceImpl fromRepository(MenuRepository menuRepository) {
    return new MenuServiceImpl(menuRepository);
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
    return toListDTO(menuRepository.findAll(new Sort("orderInMenu")));
  }

  @Override
  public List<MenuDTO> toListDTO(List<Menu> entities) {
    List<MenuDTO> menus = new ArrayList<>();
    for (Menu menu : entities) {
      menus.add(computeMenuDTOToReturn(menu));
    }
    return menus;
  }

  MenuDTO computeMenuDTOToReturn(Menu menu) {
    MenuDTO menuDTO = toDTO(menu);

    List<MenuDTO> subMenus = new ArrayList<>();
    List<Menu> children = menuRepository.findByParentId(String.valueOf(menu.getId()));
    for (Menu child : children) {
      subMenus.add(computeMenuDTOToReturn(child));
    }
    menuDTO.setChildren(subMenus);

    return menuDTO;
  }

}
