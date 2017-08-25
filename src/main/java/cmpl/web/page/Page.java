package cmpl.web.page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cmpl.web.core.model.BaseEntity;

/**
 * DAO Page
 * 
 * @author Louis
 *
 */
@Entity(name = "page")
@Table(name = "page")
public class Page extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;
  @Column(name = "menu_title")
  private String menuTitle;
  @Column(name = "body", length = 21844)
  private String body;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMenuTitle() {
    return menuTitle;
  }

  public void setMenuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
  }

}
