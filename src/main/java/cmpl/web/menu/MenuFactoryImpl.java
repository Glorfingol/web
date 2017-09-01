package cmpl.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpl.web.core.factory.BaseFactoryImpl;
import cmpl.web.message.WebMessageSourceImpl;
import cmpl.web.page.BACK_PAGE;
import cmpl.web.page.PAGES;
import cmpl.web.page.PageDTO;

/**
 * Implementation de l'interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private final MenuService menuService;

  private MenuFactoryImpl(WebMessageSourceImpl messageSource, MenuService menuService) {
    super(messageSource);
    this.menuService = menuService;
  }

  /**
   * Constructeur static pour la configuration
   * 
   * @param messageSource
   * @return
   */
  public static MenuFactoryImpl fromMessageSource(WebMessageSourceImpl messageSource, MenuService menuService) {
    return new MenuFactoryImpl(messageSource, menuService);
  }

  @Override
  public List<MenuItem> computeMenuItems(PAGES page, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    for (MENUS menu : MENUS.values()) {
      MenuItem menuItem = computeMenuItem(page, menu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  List<MenuItem> computeSubMenuItems(MENUS menu, Locale locale) {
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
  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();

    menuItems.add(computeIndexMenuElement(locale));

    for (BACK_MENU backMenu : BACK_MENU.values()) {
      MenuItem menuItem = computeMenuItem(backPage, backMenu, locale);
      menuItems.add(menuItem);
    }

    return menuItems;
  }

  MenuItem computeIndexMenuElement(Locale locale) {
    return computeMenuItem(MENUS.INDEX, locale);
  }

  MenuItem computeNewsMenuElement(Locale locale) {
    return computeMenuItem(MENUS.NEWS, locale);
  }

  MenuItem computeMenuItem(MENUS menu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(menu.getHref(), locale));
    menuItem.setLabel(getI18nValue(menu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(menu.getTitle(), locale));
    menuItem.setSubMenuItems(computeSubMenuItems(menu, locale));
    menuItem.setCustomCssClass("");

    return menuItem;
  }

  MenuItem computeMenuItem(PAGES page, MENUS menu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(menu.getHref(), locale));
    menuItem.setLabel(getI18nValue(menu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(menu.getTitle(), locale));
    menuItem.setSubMenuItems(computeSubMenuItems(menu, locale));
    String customCssClass = computeCustomCssClass(page, menu);
    menuItem.setCustomCssClass(customCssClass);

    return menuItem;
  }

  String computeCustomCssClass(PAGES page, MENUS menu) {
    return page.getTitle().equals(menu.getTitle()) ? "active" : "";
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

  MenuItem computeMenuItem(SUB_MENU subMenu, Locale locale) {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(getI18nValue(subMenu.getHref(), locale));
    menuItem.setLabel(getI18nValue(subMenu.getLabel(), locale));
    menuItem.setTitle(getI18nValue(subMenu.getLabel(), locale));
    menuItem.setSubMenuItems(new ArrayList<MenuItem>());

    return menuItem;
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
