package cmpl.web.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.builder.AbstractBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.message.impl.WebMessageSourceImpl;
import cmpl.web.model.menu.BACK_MENU;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.menu.SUB_MENU;

public class MenuBuilderImpl extends AbstractBuilder implements MenuBuilder {

  private final WebMessageSourceImpl messageSource;

  private MenuBuilderImpl(WebMessageSourceImpl messageSource) {
    this.messageSource = messageSource;
  }

  public static MenuBuilderImpl fromMessageSource(WebMessageSourceImpl messageSource) {
    return new MenuBuilderImpl(messageSource);
  }

  @Override
  public List<MenuItem> computeMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

    for (MENU menu : MENU.values()) {
      MenuItem menuItem = computeMenuItem(menu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  List<MenuItem> computeSubMenuItems(MENU menu, Locale locale) {
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    for (SUB_MENU subMenu : SUB_MENU.values()) {
      if (menu.equals(subMenu.getParent())) {
        MenuItem subMenuItem = computeMenuItem(subMenu, locale);
        subMenuItems.add(subMenuItem);
      }
    }

    return subMenuItems;
  }

  @Override
  protected String getI18nValue(String key, Locale locale) {
    return messageSource.getI18n(key, locale);
  }

  @Override
  public List<MenuItem> computeBackMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

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
