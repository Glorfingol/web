package com.cmpl.web.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.factory.BaseFactoryImpl;
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

  public MenuFactoryImpl(WebMessageSource messageSource, MenuService menuService) {
    super(messageSource);
    this.menuService = menuService;
  }

  @Override
  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    Arrays.asList(BACK_MENU.values()).forEach(backMenu -> menuItems.add(computeMenuItem(backPage, backMenu, locale)));
    return menuItems;
  }

  MenuItem computeMenuItem(BACK_PAGE backPage, BACK_MENU backMenu, Locale locale) {
    return new MenuItemBuilder().href(getI18nValue(backMenu.getHref(), locale))
        .label(getI18nValue(backMenu.getLabel(), locale)).title(getI18nValue(backMenu.getTitle(), locale))
        .subMenuItems(new ArrayList<MenuItem>()).customCssClass(computeCustomCssClass(backPage, backMenu)).build();
  }

  String computeCustomCssClass(BACK_PAGE backPage, BACK_MENU backMenu) {
    return backPage.getTitle().equals(backMenu.getTitle()) ? "active" : "";
  }

  @Override
  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    menuService.getMenus().forEach(menuItem -> menuItems.add(computeMenuItem(page, menuItem)));
    return menuItems;
  }

  MenuItem computeMenuItem(PageDTO page, MenuDTO menu) {
    return new MenuItemBuilder().href(menu.getHref()).label(menu.getLabel()).title(menu.getTitle())
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
