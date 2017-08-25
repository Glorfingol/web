package cmpl.web.core.model;

import java.time.LocalDate;

/**
 * DTO commun
 * 
 * @author Louis
 *
 */
public abstract class BaseDTO {

  private Long id;
  private LocalDate creationDate;
  private LocalDate modificationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDate modificationDate) {
    this.modificationDate = modificationDate;
  }

}
