package cmpl.web.model.menu;

import java.util.List;

public class MenuItem {

  private String title;
  private String href;
  private String label;

  List<MenuItem> subMenuItems;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<MenuItem> getSubMenuItems() {
    return subMenuItems;
  }

  public void setSubMenuItems(List<MenuItem> subMenuItems) {
    this.subMenuItems = subMenuItems;
  }

}
