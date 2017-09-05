package cmpl.web.page;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class PageUpdateForm {

  private String name;
  private String menuTitle;
  private boolean withNews;
  private String body;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate creationDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate modificationDate;

  private String nameLabel = "";
  private String menuTitleLabel = "";
  private String bodyLabel = "";
  private String withNewsLabel = "";
  private String nameHelp = "";
  private String menuTitleHelp = "";
  private String withNewsHelp = "";
  private String bodyHelp = "";

  public PageUpdateForm(PageDTO page, String nameLabel, String menuTitleLabel, String withNewsLabel, String bodyLabel,
      String nameHelp, String menuTitleHelp, String withNewsHelp, String bodyHelp) {
    this.name = page.getName();
    this.menuTitle = page.getMenuTitle();
    this.withNews = page.isWithNews();
    this.body = page.getBody();
    this.id = page.getId();
    this.creationDate = page.getCreationDate();
    this.modificationDate = page.getModificationDate();
    this.nameLabel = nameLabel;
    this.menuTitleLabel = menuTitleLabel;
    this.withNewsLabel = withNewsLabel;
    this.bodyLabel = bodyLabel;
    this.nameHelp = nameHelp;
    this.menuTitleHelp = menuTitleHelp;
    this.withNewsHelp = withNewsHelp;
    this.bodyHelp = bodyHelp;
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

  public String getNameLabel() {
    return nameLabel;
  }

  public void setNameLabel(String nameLabel) {
    this.nameLabel = nameLabel;
  }

  public String getMenuTitleLabel() {
    return menuTitleLabel;
  }

  public void setMenuTitleLabel(String menuTitleLabel) {
    this.menuTitleLabel = menuTitleLabel;
  }

  public String getWithNewsLabel() {
    return withNewsLabel;
  }

  public void setWithNewsLabel(String withNewsLabel) {
    this.withNewsLabel = withNewsLabel;
  }

  public String getNameHelp() {
    return nameHelp;
  }

  public void setNameHelp(String nameHelp) {
    this.nameHelp = nameHelp;
  }

  public String getMenuTitleHelp() {
    return menuTitleHelp;
  }

  public void setMenuTitleHelp(String menuTitleHelp) {
    this.menuTitleHelp = menuTitleHelp;
  }

  public String getWithNewsHelp() {
    return withNewsHelp;
  }

  public void setWithNewsHelp(String withNewsHelp) {
    this.withNewsHelp = withNewsHelp;
  }

  public String getBodyLabel() {
    return bodyLabel;
  }

  public void setBodyLabel(String bodyLabel) {
    this.bodyLabel = bodyLabel;
  }

  public String getBodyHelp() {
    return bodyHelp;
  }

  public void setBodyHelp(String bodyHelp) {
    this.bodyHelp = bodyHelp;
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
