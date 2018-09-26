package com.cmpl.web.core.common.event;

import com.cmpl.web.core.models.BaseEntity;

public class CreatedEvent<ENTITY extends BaseEntity> extends Event<ENTITY> {

  public CreatedEvent(Object source, ENTITY entity) {
    super(source, entity);
  }
}
