package cmpl.web.page;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class PageUpdateForm {

  private String name;
  private String menuTitle;
  private boolean withNews;
  private String body;
  private String header;
  private String footer;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate creationDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate modificationDate;

  public PageUpdateForm() {

  }

  public PageUpdateForm(PageDTO page) {
    this.name = page.getName();
    this.menuTitle = page.getMenuTitle();
    this.withNews = page.isWithNews();
    this.body = page.getBody();
    this.footer = page.getFooter();
    this.header = page.getHeader();
    this.id = page.getId();
    this.creationDate = page.getCreationDate();
    this.modificationDate = page.getModificationDate();
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
