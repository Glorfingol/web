package cmpl.web.model.news.dao;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * Objet DAO commun
 * 
 * @author Louis
 *
 */
@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "creation_date")
  private LocalDate creationDate;

  @Column(name = "modification_date")
  private LocalDate modificationDate;

  /**
   * S'assure que les elements not nullable commun sont renseignes (id, creationDate, modificationDate)
   */
  @PrePersist
  public void ensureFields() {
    if (id == null) {
      id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
    if (creationDate == null) {
      creationDate = LocalDate.now();
    }
    if (modificationDate == null) {
      modificationDate = LocalDate.now();
    }
  }

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
