package com.cmpl.web.core.models;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name = "design")
@Audited
@Table(name = "design", indexes = {@Index(name = "IDX_WEBSITE", columnList = "website_id"),
    @Index(name = "IDX_STYLE", columnList = "style_id"),
    @Index(name = "IDX_WEBSITE_STYLE", columnList = "style_id,website_id")})
public class Design extends BaseEntity {

  @Column(name = "style_id")
  private Long styleId;

  @Column(name = "website_id")
  private Long websiteId;

  public Long getStyleId() {
    return styleId;
  }

  public void setStyleId(Long styleId) {
    this.styleId = styleId;
  }

  public Long getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(Long websiteId) {
    this.websiteId = websiteId;
  }
}
