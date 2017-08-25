package cmpl.web.meta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cmpl.web.core.model.BaseEntity;

@Entity(name = "metaElement")
@Table(name = "meta_element")
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
