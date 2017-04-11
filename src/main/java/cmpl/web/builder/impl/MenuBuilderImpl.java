package cmpl.web.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

import cmpl.web.builder.AbstractBuilder;
import cmpl.web.builder.MenuBuilder;
import cmpl.web.model.menu.BACK_MENU;
import cmpl.web.model.menu.MENU;
import cmpl.web.model.menu.MenuItem;
import cmpl.web.model.menu.SUB_MENU;

public class MenuBuilderImpl extends AbstractBuilder implements MenuBuilder {

  private final ResourceBundleMessageSource resourceBundleMessageSource;

  public static MenuBuilderImpl fromResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
    return new MenuBuilderImpl(resourceBundleMessageSource);
  }

  private MenuBuilderImpl(ResourceBundleMessageSource resourceBundleMessageSource) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
  }

  @Override
  public List<MenuItem> computeMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

    for (MENU menu : MENU.values()) {
      MenuItem menuItem = new MenuItem();
      menuItem.setHref(getI18nValue(menu.getHref(), locale));
      menuItem.setLabel(getI18nValue(menu.getLabel(), locale));
      menuItem.setTitle(getI18nValue(menu.getTitle(), locale));

      menuItem.setSubMenuItems(computeSubMenuItems(menu, locale));

      menuItems.add(menuItem);
    }

    return menuItems;
  }

  private List<MenuItem> computeSubMenuItems(MENU menu, Locale locale) {
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    for (SUB_MENU subMenu : SUB_MENU.values()) {
      if (menu.equals(subMenu.getParent())) {
        MenuItem subMenuItem = new MenuItem();

        subMenuItem.setHref(getI18nValue(subMenu.getHref(), locale));
        subMenuItem.setLabel(getI18nValue(subMenu.getLabel(), locale));
        subMenuItem.setTitle(getI18nValue(subMenu.getLabel(), locale));

        subMenuItems.add(subMenuItem);

      }
    }

    return subMenuItems;
  }

  @Override
  protected String getI18nValue(String key, Locale locale) {
    return resourceBundleMessageSource.getMessage(key, null, locale);
  }

  @Override
  public List<MenuItem> computeBackMenuItems(Locale locale) {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

    MenuItem accueil = new MenuItem();
    accueil.setHref(getI18nValue(MENU.INDEX.getHref(), locale));
    accueil.setLabel(getI18nValue(MENU.INDEX.getLabel(), locale));
    accueil.setTitle(getI18nValue(MENU.INDEX.getTitle(), locale));
    accueil.setSubMenuItems(new ArrayList<MenuItem>());

    menuItems.add(accueil);

    for (BACK_MENU menu : BACK_MENU.values()) {
      MenuItem menuItem = new MenuItem();
      menuItem.setHref(getI18nValue(menu.getHref(), locale));
      menuItem.setLabel(getI18nValue(menu.getLabel(), locale));
      menuItem.setTitle(getI18nValue(menu.getTitle(), locale));

      menuItem.setSubMenuItems(new ArrayList<MenuItem>());
      menuItems.add(menuItem);
    }

    return menuItems;
  }
}
