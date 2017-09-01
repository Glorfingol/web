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
  @Column(name = "with_news")
  private boolean withNews;

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

  public boolean isWithNews() {
    return withNews;
  }

  public void setWithNews(boolean withNews) {
    this.withNews = withNews;
  }

}
