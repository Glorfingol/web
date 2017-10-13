package cmpl.web.carousel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cmpl.web.core.model.BaseEntity;

@Entity(name = "carousel")
@Table(name = "carousel")
public class Carousel extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;
  @Column(name = "page_id")
  private String pageId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

}
