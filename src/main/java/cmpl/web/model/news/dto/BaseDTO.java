package cmpl.web.model.news.dto;

import java.util.Date;

/**
 * DTO commun
 * 
 * @author Louis
 *
 */
public abstract class BaseDTO {

  private Long id;
  private Date creationDate;
  private Date modificationDate;

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
