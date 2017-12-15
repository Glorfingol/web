package com.cmpl.web.meta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.model.BaseEntity;

@Entity(name = "metaElement")
@Table(name = "meta_element", indexes = {@Index(name = "IDX_META", columnList = "page_id,name", unique = true)})
public class MetaElement extends BaseEntity {

  @Column(name = "page_id")
  private String pageId;
  @Column(name = "name")
  private String name;
  @Column(name = "content")
  private String content;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
