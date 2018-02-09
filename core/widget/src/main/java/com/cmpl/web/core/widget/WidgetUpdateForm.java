package com.cmpl.web.core.widget;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class WidgetUpdateForm {

  private WIDGET_TYPE type;
  private String entityId;
  private String name;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate creationDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate modificationDate;
  private String personalization;
  private String localeCode;

  public WIDGET_TYPE getType() {
    return type;
  }

  public void setType(WIDGET_TYPE type) {
    this.type = type;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getPersonalization() {
    return personalization;
  }

  public void setPersonalization(String personalization) {
    this.personalization = personalization;
  }

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }
}
