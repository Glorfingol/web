package cmpl.web.model.news.rest.news;

import java.util.Date;

public class NewsImageRequest {

  private String src;
  private String legend;
  private String alt;
  private Long id;
  private Date creationDate;
  private Date modificationDate;

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
  }

}
