package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class WidgetPageServiceImpl extends BaseServiceImpl<WidgetPageDTO,WidgetPage> implements  WidgetPageService {


  public WidgetPageServiceImpl(
     WidgetPageRepository widgetPageRepository) {
    super(widgetPageRepository);
  }

  @Override
  protected WidgetPageDTO toDTO(WidgetPage entity) {
    WidgetPageDTO dto = WidgetPageDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected WidgetPage toEntity(WidgetPageDTO dto) {
    WidgetPage entity = WidgetPageBuilder.create().build();
    fillObject(dto,entity);
    return entity;
  }
}
