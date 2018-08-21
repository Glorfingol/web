package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.service.BaseService;
import java.util.List;

/**
 * Interface des menus
 *
 * @author Louis
 */
public interface MenuService extends BaseService<MenuDTO> {

  /**
   * Recuperer tous les elements du menu
   */
  List<MenuDTO> getMenus();

}
