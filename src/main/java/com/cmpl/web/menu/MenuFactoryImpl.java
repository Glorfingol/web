package com.cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.factory.BaseFactoryImpl;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.message.WebMessageSource;
import com.cmpl.web.page.BACK_PAGE;
import com.cmpl.web.page.PageDTO;

/**
 * Implementation de l'interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private final MenuService menuService;
  private final BackMenu backMenu;

  public MenuFactoryImpl(WebMessageSource messageSource, MenuService menuService, BackMenu backMenu) {
    super(messageSource);
    this.menuService = menuService;
    this.backMenu = backMenu;
  }

  @Override
  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    backMenu.getItems().forEach(item -> menuItems.add(computeMenuItem(backPage, item, locale)));
    return menuItems;
  }

  MenuItem computeMenuItem(BACK_PAGE backPage, BackMenuItem item, Locale locale) {
    return MenuItemBuilder.create().href(getI18nValue(item.getHref(), locale))
        .label(getI18nValue(item.getLabel(), locale)).title(getI18nValue(item.getTitle(), locale))
        .subMenuItems(new ArrayList<MenuItem>()).customCssClass(computeCustomCssClass(backPage, item)).build();
  }

  String computeCustomCssClass(BACK_PAGE backPage, BackMenuItem item) {
    return backPage.getTitle().equals(item.getTitle()) ? "active" : "";
  }

  @Override
  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    menuService.getMenus().forEach(menuItem -> menuItems.add(computeMenuItem(page, menuItem)));
    return menuItems;
  }

  MenuItem computeMenuItem(PageDTO page, MenuDTO menu) {
    return MenuItemBuilder.create().href(menu.getHref()).label(menu.getLabel()).title(menu.getTitle())
        .customCssClass(computeCustomCssClass(page, menu)).subMenuItems(computeSubMenuItems(page, menu)).build();
  }

  String computeCustomCssClass(PageDTO page, MenuDTO menu) {
    return page.getId().equals(Long.parseLong(menu.getPageId())) ? "active" : "";
  }

  List<MenuItem> computeSubMenuItems(PageDTO page, MenuDTO menu) {
    List<MenuItem> subMenuItems = new ArrayList<>();
    menu.getChildren().forEach(subMenu -> subMenuItems.add(computeMenuItem(page, subMenu)));
    return subMenuItems;
  }

}
