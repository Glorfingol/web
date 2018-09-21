package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.Builder;
import java.time.LocalDateTime;

public class PageUpdateFormBuilder extends Builder<PageUpdateForm> {

  private String name;

  private String menuTitle;

  private String body;

  private String header;

  private String footer;

  private String meta;

  private String href;

  private String amp;

  private Long id;

  private LocalDateTime creationDate;

  private LocalDateTime modificationDate;

  private String creationUser;

  private String modificationUser;

  private String localeCode;

  private boolean indexed;

  private PageUpdateFormBuilder() {

  }

  public PageUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageUpdateFormBuilder href(String href) {
    this.href = href;
    return this;
  }

  public PageUpdateFormBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageUpdateFormBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageUpdateFormBuilder header(String header) {
    this.header = header;
    return this;
  }

  public PageUpdateFormBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  public PageUpdateFormBuilder meta(String meta) {
    this.meta = meta;
    return this;
  }

  public PageUpdateFormBuilder amp(String amp) {
    this.amp = amp;
    return this;
  }

  public PageUpdateFormBuilder localeCode(String localeCode) {
    this.localeCode = localeCode;
    return this;
  }

  public PageUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public PageUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public PageUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public PageUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public PageUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }


  public PageUpdateFormBuilder indexed(boolean indexed) {
    this.indexed = indexed;
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
    form.setLocaleCode(localeCode);
    form.setMeta(meta);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    form.setAmp(amp);
    form.setHref(href);
    form.setIndexed(indexed);
    return form;
  }

  public static PageUpdateFormBuilder create() {
    return new PageUpdateFormBuilder();
  }

}
