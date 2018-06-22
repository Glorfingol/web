package com.cmpl.web.core.association_entity_group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "associationEntityGroup")
@Table(name = "association_entity_group", indexes = {@Index(name = "IDX_ENTITY", columnList = "entity_id"),
    @Index(name = "IDX_GROUP", columnList = "group_id")})
public class AssociationEntityGroup extends BaseEntity {

  @Column(name = "entity_id")
  private String entityId;
  @Column(name = "group_id")
  private String groupId;

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
}
