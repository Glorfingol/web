package com.cmpl.web.page;

import com.cmpl.web.core.builder.BaseBuilder;

public class PageBuilder extends BaseBuilder<Page> {

  private String name;
  private String menuTitle;
  private boolean withNews;

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

  public PageBuilder withNews(boolean withNews) {
    this.withNews = withNews;
    return this;
  }

  @Override
  public Page build() {
    Page page = new Page();
    page.setCreationDate(creationDate);
    page.setId(id);
    page.setMenuTitle(menuTitle);
    page.setModificationDate(modificationDate);
    page.setName(name);
    page.setWithNews(withNews);
    return page;
  }

  public static PageBuilder create() {
    return new PageBuilder();
  }

}
