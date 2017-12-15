package com.cmpl.web.meta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.model.BaseEntity;

@Entity(name = "openGraphMetaElement")
@Table(name = "open_graph_meta_element", indexes = {@Index(name = "IDX_META", columnList = "page_id,property", unique = true)})
public class OpenGraphMetaElement extends BaseEntity {

  @Column(name = "page_id")
  private String pageId;
  @Column(name = "content")
  private String content;
  @Column(name = "property")
  private String property;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

}
