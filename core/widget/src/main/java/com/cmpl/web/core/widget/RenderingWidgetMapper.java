package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Widget;

public class RenderingWidgetMapper extends BaseMapper<RenderingWidgetDTO, Widget> {

  @Override
  public RenderingWidgetDTO toDTO(Widget entity) {
    if (entity == null) {
      return null;
    }
    RenderingWidgetDTO dto = RenderingWidgetDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Widget toEntity(RenderingWidgetDTO dto) {
    Widget entity = WidgetBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
