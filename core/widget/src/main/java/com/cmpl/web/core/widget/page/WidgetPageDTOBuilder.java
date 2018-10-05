package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetPageDTOBuilder extends BaseBuilder<WidgetPageDTO> {

  private Long pageId;

  private Long widgetId;

  public WidgetPageDTOBuilder pageId(Long pageId) {
    this.pageId = pageId;
    return this;
  }

  public WidgetPageDTOBuilder widgetId(Long widgetId) {
    this.widgetId = widgetId;
    return this;
  }

  private WidgetPageDTOBuilder() {

  }

  @Override
  public WidgetPageDTO build() {
    WidgetPageDTO widgetPageDTO = new WidgetPageDTO();
    widgetPageDTO.setPageId(pageId);
    widgetPageDTO.setWidgetId(widgetId);
    widgetPageDTO.setCreationDate(creationDate);
    widgetPageDTO.setCreationUser(creationUser);
    widgetPageDTO.setModificationUser(modificationUser);
    widgetPageDTO.setId(id);
    widgetPageDTO.setModificationDate(modificationDate);
    return widgetPageDTO;
  }

  public static WidgetPageDTOBuilder create() {
    return new WidgetPageDTOBuilder();
  }
}
