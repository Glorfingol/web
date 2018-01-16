package com.cmpl.web.menu;

import java.util.List;

import com.cmpl.web.core.builder.Builder;

public class MenuItemBuilder extends Builder<MenuItem> {

  private String href;
  private String label;
  private String title;
  private List<MenuItem> subMenuItems;
  private String customCssClass;

  private MenuItemBuilder() {
  }

  public MenuItemBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuItemBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuItemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuItemBuilder subMenuItems(List<MenuItem> subMenuItems) {
    this.subMenuItems = subMenuItems;
    return this;
  }

  public MenuItemBuilder customCssClass(String customCssClass) {
    this.customCssClass = customCssClass;
    return this;
  }

  @Override
  public MenuItem build() {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(href);
    menuItem.setLabel(label);
    menuItem.setTitle(title);
    menuItem.setSubMenuItems(subMenuItems);
    menuItem.setCustomCssClass(customCssClass);

    return menuItem;
  }

  public static MenuItemBuilder create() {
    return new MenuItemBuilder();
  }

}
