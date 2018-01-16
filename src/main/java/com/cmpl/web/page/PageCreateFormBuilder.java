package com.cmpl.web.page;

import com.cmpl.web.core.builder.Builder;

public class PageCreateFormBuilder extends Builder<PageCreateForm> {

  private String name = "";
  private String menuTitle = "";
  private boolean withNews = false;
  private String body = "";
  private String header = "";
  private String footer = "";

  private PageCreateFormBuilder() {

  }

  public PageCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageCreateFormBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageCreateFormBuilder withNews(boolean withNews) {
    this.withNews = withNews;
    return this;
  }

  public PageCreateFormBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageCreateFormBuilder header(String header) {
    this.header = header;
    return this;
  }

  public PageCreateFormBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  @Override
  public PageCreateForm build() {
    PageCreateForm form = new PageCreateForm();
    form.setBody(body);
    form.setFooter(footer);
    form.setHeader(header);
    form.setMenuTitle(menuTitle);
    form.setName(name);
    form.setWithNews(withNews);
    return form;
  }

  public static PageCreateFormBuilder create() {
    return new PageCreateFormBuilder();
  }

}
