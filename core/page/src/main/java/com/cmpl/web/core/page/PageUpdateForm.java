package com.cmpl.web.core.page;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class PageUpdateForm {

  private String name;
  private String menuTitle;
  private String body;
  private String header;
  private String footer;
  private String meta;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime creationDate;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime modificationDate;
  private String localeCode;

  public PageUpdateForm() {

  }

  public PageUpdateForm(PageDTO page, String personalizationLanguageCode) {
    this.name = page.getName();
    this.menuTitle = page.getMenuTitle();
    this.body = page.getBody();
    this.footer = page.getFooter();
    this.header = page.getHeader();
    this.id = page.getId();
    this.creationDate = page.getCreationDate();
    this.modificationDate = page.getModificationDate();
    this.localeCode = personalizationLanguageCode;
    this.meta = page.getMeta();
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

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDateTime modificationDate) {
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

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }
}
