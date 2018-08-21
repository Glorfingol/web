package com.cmpl.web.core.factory.menu;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.BaseFactoryImpl;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation de l'interface pour la factory du menu
 *
 * @author Louis
 */
public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private final MenuService menuService;

  private final BackMenu backMenu;

  public MenuFactoryImpl(WebMessageSource messageSource, MenuService menuService,
      BackMenu backMenu) {
    super(messageSource);
    this.menuService = Objects.requireNonNull(menuService);

    this.backMenu = Objects.requireNonNull(backMenu);

  }

  @Override
  public List<MenuItem> computeBackMenuItems(BackPage backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    List<BackMenuItem> parents = backMenu.getItems().stream()
        .filter(item -> item.getParent() == null)
        .collect(Collectors.toList());
    parents.forEach(parent -> {
      List<BackMenuItem> children = backMenu.getItems().stream()
          .filter(item -> parent.equals(item.getParent()))
          .collect(Collectors.toList());
      List<MenuItem> childrenItems = new ArrayList<>();
      children
          .forEach(childItem -> childrenItems.add(computeMenuItem(backPage, childItem, locale)));
      menuItems.add(computeMenuItem(backPage, parent, locale, childrenItems, children));
    });
    return menuItems;
  }

  MenuItem computeMenuItem(BackPage backPage, BackMenuItem item, Locale locale) {
    return MenuItemBuilder.create().href(item.getHref())
        .label(getI18nValue(item.getLabel(), locale))
        .title(getI18nValue(item.getTitle(), locale)).subMenuItems(new ArrayList<>())
        .customCssClass(computeCustomCssClass(backPage, item)).iconClass(item.getIconClass())
        .privilege(item.getPrivilege()).build();
  }

  MenuItem computeMenuItem(BackPage backPage, BackMenuItem item, Locale locale,
      List<MenuItem> children,
      List<BackMenuItem> untransformedChildren) {
    return MenuItemBuilder.create().href(item.getHref())
        .label(getI18nValue(item.getLabel(), locale))
        .title(getI18nValue(item.getTitle(), locale)).subMenuItems(children)
        .customCssClass(computeCustomCssClass(backPage, item, untransformedChildren))
        .iconClass(item.getIconClass())
        .privilege(item.getPrivilege()).build();
  }

  String computeCustomCssClass(BackPage backPage, BackMenuItem item) {
    return isItemActive(backPage, item) ? "active" : "";
  }

  String computeCustomCssClass(BackPage backPage, BackMenuItem item, List<BackMenuItem> children) {
    return (isItemActive(backPage, item) || isAnyChildActive(backPage, children)) ? "active" : "";
  }

  boolean isItemActive(BackPage backPage, BackMenuItem item) {
    return backPage.getTitleKey().equals(item.getTitle());
  }

  boolean isAnyChildActive(BackPage backPage, List<BackMenuItem> children) {
    List<Boolean> anyTrue = children.stream()
        .map(child -> child.getTitle().equals(backPage.getTitleKey()))
        .collect(Collectors.toList());
    return anyTrue.contains(Boolean.TRUE);
  }

  @Override
  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuService.getMenus().stream().map(menuItem -> computeMenuItem(page, menuItem))
        .collect(Collectors.toList());
  }

  MenuItem computeMenuItem(PageDTO page, MenuDTO menu) {
    return MenuItemBuilder.create().href(menu.getHref()).label(menu.getLabel())
        .title(menu.getTitle())
        .customCssClass(computeCustomCssClass(page, menu))
        .subMenuItems(computeSubMenuItems(page, menu)).build();
  }

  String computeCustomCssClass(PageDTO page, MenuDTO menu) {
    return page.getId().equals(Long.parseLong(menu.getPageId())) ? "active" : "";
  }

  List<MenuItem> computeSubMenuItems(PageDTO page, MenuDTO menu) {
    return menu.getChildren().stream().map(subMenuItem -> computeMenuItem(page, subMenuItem))
        .collect(Collectors.toList());
  }

}
