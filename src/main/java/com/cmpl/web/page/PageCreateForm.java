package com.cmpl.web.page;

public class PageCreateForm {

  private String name = "";
  private String menuTitle = "";
  private boolean withNews = false;
  private String body = "";
  private String header = "";
  private String footer = "";

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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

}
