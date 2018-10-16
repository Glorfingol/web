package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "website")
@Table(name = "website")
public class Website extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "extension")
  private String extension;

  @Column(name = "is_secure")
  private boolean secure;

  @Column(name = "is_system_jquery")
  private boolean systemJquery;

  @Column(name = "is_system_fontawesome")
  private boolean systemFontAwesome;

  @Column(name = "is_system_bootstrap")
  private boolean systemBootstrap;

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public boolean isSystemJquery() {
    return systemJquery;
  }

  public void setSystemJquery(boolean systemJquery) {
    this.systemJquery = systemJquery;
  }

  public boolean isSystemBootstrap() {
    return systemBootstrap;
  }

  public void setSystemBootstrap(boolean systemBootstrap) {
    this.systemBootstrap = systemBootstrap;
  }

  public boolean isSystemFontAwesome() {
    return systemFontAwesome;
  }

  public void setSystemFontAwesome(boolean systemFontAwesome) {
    this.systemFontAwesome = systemFontAwesome;
  }
}
