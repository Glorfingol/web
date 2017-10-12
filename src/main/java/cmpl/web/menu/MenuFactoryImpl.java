package cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.core.factory.BaseFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.page.BACK_PAGE;
import cmpl.web.page.PageDTO;

/**
 * Implementation de l'interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private final MenuService menuService;

  public MenuFactoryImpl(WebMessageSourceImpl messageSource, MenuService menuService) {
    super(messageSource);
    this.menuService = menuService;
  }

  @Override
  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    for (BACK_MENU backMenu : BACK_MENU.values()) {
      MenuItem menuItem = computeMenuItem(backPage, backMenu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  MenuItem computeMenuItem(BACK_PAGE backPage, BACK_MENU backMenu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(backMenu.getHref(), locale));
    menuItem.setLabel(getI18nValue(backMenu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(backMenu.getTitle(), locale));
    menuItem.setSubMenuItems(new ArrayList<MenuItem>());
    String customCssClass = computeCustomCssClass(backPage, backMenu);
    menuItem.setCustomCssClass(customCssClass);

    return menuItem;
  }

  String computeCustomCssClass(BACK_PAGE backPage, BACK_MENU backMenu) {
    return backPage.getTitle().equals(backMenu.getTitle()) ? "active" : "";
  }

  @Override
  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    List<MenuDTO> menus = menuService.getMenus();
    for (MenuDTO menuItem : menus) {
      menuItems.add(computeMenuItem(page, menuItem));
    }
    return menuItems;
  }

  MenuItem computeMenuItem(PageDTO page, MenuDTO menu) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(menu.getHref());
    menuItem.setLabel(menu.getLabel());
    menuItem.setTitle(menu.getTitle());
    menuItem.setSubMenuItems(new ArrayList<MenuItem>());
    menuItem.setCustomCssClass(computeCustomCssClass(page, menu));
    menuItem.setSubMenuItems(computeSubMenuItems(page, menu));

    return menuItem;
  }

  String computeCustomCssClass(PageDTO page, MenuDTO menu) {
    return page.getId().equals(Long.parseLong(menu.getPageId())) ? "active" : "";
  }

  List<MenuItem> computeSubMenuItems(PageDTO page, MenuDTO menu) {
    List<MenuItem> subMenuItems = new ArrayList<>();
    for (MenuDTO subMenu : menu.getChildren()) {
      MenuItem subMenuItem = computeMenuItem(page, subMenu);
      subMenuItems.add(subMenuItem);
    }

    return subMenuItems;
  }

}
