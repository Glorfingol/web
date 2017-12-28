package com.cmpl.web.page;

import java.time.LocalDate;

import com.cmpl.web.core.builder.Builder;

public class PageUpdateFormBuilder extends Builder<PageUpdateForm> {

  private String name;
  private String menuTitle;
  private boolean withNews;
  private String body;
  private String header;
  private String footer;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  public PageUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageUpdateFormBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageUpdateFormBuilder withNews(boolean withNews) {
    this.withNews = withNews;
    return this;
  }

  public PageUpdateFormBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageUpdateFormBuilder hader(String header) {
    this.header = header;
    return this;
  }

  public PageUpdateFormBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  public PageUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public PageUpdateFormBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public PageUpdateFormBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  @Override
  public PageUpdateForm build() {
    PageUpdateForm form = new PageUpdateForm();
    form.setBody(body);
    form.setCreationDate(creationDate);
    form.setFooter(footer);
    form.setHeader(header);
    form.setId(id);
    form.setMenuTitle(menuTitle);
    form.setMenuTitle(menuTitle);
    form.setModificationDate(modificationDate);
    form.setName(name);
    form.setWithNews(withNews);
    return form;
  }

}
