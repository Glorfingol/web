package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name = "responsibility")
@Table(name = "responsibility", indexes = {@Index(name = "IDX_USER", columnList = "user_id"),
  @Index(name = "IDX_ROLE", columnList = "role_id"),
  @Index(name = "IDX_ROLE_USER", columnList = "role_id,user_id")})
public class Responsibility extends BaseEntity {

  @Column(name = "user_id", length = 20)
  private Long userId;

  @Column(name = "role_id", length = 20)
  private Long roleId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
