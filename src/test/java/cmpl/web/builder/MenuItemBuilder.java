package cmpl.web.builder;

import java.util.List;

import cmpl.web.model.menu.MenuItem;

public class MenuItemBuilder {

  private String href;
  private String label;
  private String title;
  private List<MenuItem> subMenuItems;

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

  public MenuItem toMenuItem() {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(href);
    menuItem.setLabel(label);
    menuItem.setTitle(title);
    menuItem.setSubMenuItems(subMenuItems);

    return menuItem;
  }

}
