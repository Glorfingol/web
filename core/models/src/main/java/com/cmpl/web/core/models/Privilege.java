package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name = "privilege")
@Table(name = "privilege", indexes = {@Index(name = "IDX_PRIVILEGE", columnList = "role_id")})
public class Privilege extends BaseEntity {

  @Column(name = "role_id", length = 20)
  private Long roleId;

  @Column(name = "content")
  private String content;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
