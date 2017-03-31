package cmpl.web.model.news.dao;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "creation_date")
  private Date creationDate;

  @Column(name = "modification_date")
  private Date modificationDate;

  @Column(name = "creation_user")
  private String creationUser;

  @Column(name = "modification_user")
  private String modificationUser;

  @PrePersist
  public void ensureFields() {
    if (id == null) {
      id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
    if (creationDate == null) {
      creationDate = new Date(System.currentTimeMillis());
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
