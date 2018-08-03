package com.cmpl.web.core.models;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "carousel")
@Table(name = "carousel")
@Audited
public class Carousel extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
