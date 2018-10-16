package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.Builder;
import java.time.LocalDateTime;

public class WebsiteUpdateFormBuilder extends Builder<WebsiteUpdateForm> {

  private String name;

  private String description;

  private String extension;

  private boolean secure;

  private Long id;

  private LocalDateTime creationDate;

  private LocalDateTime modificationDate;

  private String creationUser;

  private String modificationUser;


  private Boolean systemJquery;

  private Boolean systemBootstrap;

  private Boolean systemFontAwesome;

  private WebsiteUpdateFormBuilder() {

  }

  public WebsiteUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteUpdateFormBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public WebsiteUpdateFormBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  public WebsiteUpdateFormBuilder systemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
    return this;
  }

  public WebsiteUpdateFormBuilder systemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
    return this;
  }

  public WebsiteUpdateFormBuilder systemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
    return this;
  }

  public WebsiteUpdateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public WebsiteUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public WebsiteUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public WebsiteUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public WebsiteUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public WebsiteUpdateForm build() {
    WebsiteUpdateForm form = new WebsiteUpdateForm();
    form.setDescription(description);
    form.setName(name);
    form.setSecure(secure);
    form.setId(id);
    form.setCreationDate(creationDate);
    form.setModificationDate(modificationDate);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    form.setExtension(extension);
    form.setSystemBootstrap(systemBootstrap);
    form.setSystemFontAwesome(systemFontAwesome);
    form.setSystemJquery(systemJquery);
    return form;
  }

  public static WebsiteUpdateFormBuilder create() {
    return new WebsiteUpdateFormBuilder();
  }
}
