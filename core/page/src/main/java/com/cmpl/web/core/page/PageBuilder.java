package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Page;

public class PageBuilder extends BaseBuilder<Page> {

  private String name;

  private String menuTitle;

  private String href;

  private boolean indexed;

  private PageBuilder() {

  }

  public PageBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageBuilder indexed(boolean indexed) {
    this.indexed = indexed;
    return this;
  }

  public PageBuilder href(String href) {
    this.href = href;
    return this;
  }

  @Override
  public Page build() {
    Page page = new Page();
    page.setMenuTitle(menuTitle);
    page.setName(name);
    page.setHref(href);
    page.setCreationDate(creationDate);
    page.setCreationUser(creationUser);
    page.setModificationUser(modificationUser);
    page.setId(id);
    page.setModificationDate(modificationDate);
    page.setIndexed(indexed);
    return page;
  }

  public static PageBuilder create() {
    return new PageBuilder();
  }

}
