package com.cmpl.web.menu;

import java.util.List;

import com.cmpl.web.core.service.BaseService;

/**
 * Interface des menus
 * 
 * @author Louis
 *
 */
public interface MenuService extends BaseService<MenuDTO> {

  /**
   * Recuperer tous les elements du menu
   * 
   * @return
   */
  List<MenuDTO> getMenus();

}