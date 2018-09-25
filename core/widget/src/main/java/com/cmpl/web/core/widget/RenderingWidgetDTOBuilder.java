package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RenderingWidgetDTOBuilder extends BaseBuilder<RenderingWidgetDTO> {

  private String type;

  private String entityId;

  private String name;


  private boolean asynchronous;

  private RenderingWidgetDTOBuilder() {

  }

  public RenderingWidgetDTOBuilder type(String type) {
    this.type = type;
    return this;
  }

  public RenderingWidgetDTOBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public RenderingWidgetDTOBuilder name(String name) {
    this.name = name;
    return this;
  }


  public RenderingWidgetDTOBuilder asynchronous(boolean asynchronous) {
    this.asynchronous = asynchronous;
    return this;
  }

  @Override
  public RenderingWidgetDTO build() {
    RenderingWidgetDTO widgetDTO = new RenderingWidgetDTO();
    widgetDTO.setEntityId(entityId);
    widgetDTO.setName(name);
    widgetDTO.setType(type);
    widgetDTO.setAsynchronous(asynchronous);
    widgetDTO.setCreationDate(creationDate);
    widgetDTO.setCreationUser(creationUser);
    widgetDTO.setModificationUser(modificationUser);
    widgetDTO.setId(id);
    widgetDTO.setModificationDate(modificationDate);
    return widgetDTO;
  }

  public static RenderingWidgetDTOBuilder create() {
    return new RenderingWidgetDTOBuilder();
  }
}
