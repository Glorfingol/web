package cmpl.web.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.factory.MenuFactory;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.menu.BACK_MENU;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.menu.SUB_MENU;

public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private MenuFactoryImpl(WebMessageSourceImpl messageSource) {
    super(messageSource);
  }

  public static MenuFactoryImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new MenuFactoryImpl(messageSource);
  }

  @Override
  public List<MenuItem> computeMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    for (MENU menu : MENU.values()) {
      MenuItem menuItem = computeMenuItem(menu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  List<MenuItem> computeSubMenuItems(MENU menu, Locale locale) {
    List<MenuItem> subMenuItems = new ArrayList<>();
    for (SUB_MENU subMenu : SUB_MENU.values()) {
      if (menu.equals(subMenu.getParent())) {
        MenuItem subMenuItem = computeMenuItem(subMenu, locale);
        subMenuItems.add(subMenuItem);
      }
    }

    return subMenuItems;
  }

  @Override
  public List<MenuItem> computeBackMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    menuItems.add(computeIndexMenuElement(locale));

    for (BACK_MENU backMenu : BACK_MENU.values()) {
      MenuItem menuItem = computeMenuItem(backMenu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  MenuItem computeIndexMenuElement(Locale locale) {
    return computeMenuItem(MENU.INDEX, locale);
  }

  MenuItem computeMenuItem(MENU menu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(menu.getHref(), locale));
    menuItem.setLabel(getI18nValue(menu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(menu.getTitle(), locale));
    menuItem.setSubMenuItems(computeSubMenuItems(menu, locale));

    return menuItem;
  }

  MenuItem computeMenuItem(BACK_MENU backMenu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(backMenu.getHref(), locale));
    menuItem.setLabel(getI18nValue(backMenu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(backMenu.getTitle(), locale));
    menuItem.setSubMenuItems(new ArrayList<MenuItem>());

    return menuItem;
  }

  MenuItem computeMenuItem(SUB_MENU subMenu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(subMenu.getHref(), locale));
    menuItem.setLabel(getI18nValue(subMenu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(subMenu.getLabel(), locale));
    menuItem.setSubMenuItems(new ArrayList<MenuItem>());

    return menuItem;
  }

}
