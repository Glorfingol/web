package com.cmpl.web.menu;

import java.time.LocalDate;

public class MenuUpdateForm {

  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  public MenuUpdateForm(MenuDTO menuDTO) {
    this.id = menuDTO.getId();
    this.creationDate = menuDTO.getCreationDate();
    this.modificationDate = menuDTO.getModificationDate();
    this.title = menuDTO.getTitle();
    this.href = menuDTO.getHref();
    this.label = menuDTO.getLabel();
    this.orderInMenu = menuDTO.getOrderInMenu();
    this.pageId = menuDTO.getPageId();
    this.parentId = menuDTO.getParentId();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public int getOrderInMenu() {
    return orderInMenu;
  }

  public void setOrderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
  }

}
