package cmpl.web.menu;

import java.util.List;

/**
 * Interface des menus
 * 
 * @author Louis
 *
 */
public interface MenuService {

  /**
   * Recuperer tous les elements du menu
   * 
   * @return
   */
  List<MenuDTO> getMenus();

}
