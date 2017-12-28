package com.cmpl.web.menu;

import java.time.LocalDate;

import com.cmpl.web.core.builder.Builder;

public class MenuUpdateFormBuilder extends Builder<MenuUpdateForm> {

  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  public MenuUpdateFormBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuUpdateFormBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuUpdateFormBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuUpdateFormBuilder orderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
    return this;
  }

  public MenuUpdateFormBuilder parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  public MenuUpdateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public MenuUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public MenuUpdateFormBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public MenuUpdateFormBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  @Override
  public MenuUpdateForm build() {
    MenuUpdateForm form = new MenuUpdateForm();
    form.setCreationDate(creationDate);
    form.setHref(href);
    form.setId(id);
    form.setLabel(label);
    form.setModificationDate(modificationDate);
    form.setOrderInMenu(orderInMenu);
    form.setPageId(pageId);
    form.setParentId(parentId);
    form.setTitle(title);
    return form;
  }

}
