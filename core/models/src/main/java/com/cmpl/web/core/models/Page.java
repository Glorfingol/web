package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * DAO Page
 *
 * @author Louis
 */
@Entity(name = "page")
@Table(name = "page", indexes = {@Index(name = "IDX_HREF", columnList = "href")})
public class Page extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "menu_title")
  private String menuTitle;

  @Column(name = "href")
  private String href;

  @Column(name = "indexed")
  private boolean indexed;

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

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public boolean isIndexed() {
    return indexed;
  }

  public void setIndexed(boolean indexed) {
    this.indexed = indexed;
  }
}
