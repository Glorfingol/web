package com.cmpl.web.core.widget;

import java.time.LocalDate;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetUpdateFormBuilder extends Builder<WidgetUpdateForm> {

  private WIDGET_TYPE type;
  private String entityId;
  private String name;
  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;
  private String personalization;
  private String localeCode;

  public WidgetUpdateFormBuilder type(WIDGET_TYPE type) {
    this.type = type;
    return this;
  }

  public WidgetUpdateFormBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public WidgetUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WidgetUpdateFormBuilder localeCode(String localeCode) {
    this.localeCode = localeCode;
    return this;
  }

  public WidgetUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public WidgetUpdateFormBuilder creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public WidgetUpdateFormBuilder modificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public WidgetUpdateFormBuilder personalization(String personalization) {
    this.personalization = personalization;
    return this;
  }

  private WidgetUpdateFormBuilder() {

  }

  @Override
  public WidgetUpdateForm build() {
    WidgetUpdateForm form = new WidgetUpdateForm();
    form.setCreationDate(creationDate);
    form.setEntityId(entityId);
    form.setId(id);
    form.setModificationDate(modificationDate);
    form.setName(name);
    form.setType(type);
    form.setPersonalization(personalization);
    form.setLocaleCode(localeCode);
    return form;
  }

  public static WidgetUpdateFormBuilder create() {
    return new WidgetUpdateFormBuilder();
  }
}
