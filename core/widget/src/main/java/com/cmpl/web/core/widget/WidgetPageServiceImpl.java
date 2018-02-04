package com.cmpl.web.core.widget;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class WidgetPageServiceImpl extends BaseServiceImpl<WidgetPageDTO, WidgetPage> implements WidgetPageService {

  private final WidgetPageRepository widgetPageRepository;

  public WidgetPageServiceImpl(WidgetPageRepository widgetPageRepository) {
    super(widgetPageRepository);
    this.widgetPageRepository = widgetPageRepository;
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
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public List<WidgetPageDTO> findByPageId(String pageId) {
    return toListDTO(widgetPageRepository.findByPageId(pageId));
  }

  @Override
  public WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId) {
    return toDTO(widgetPageRepository.findByPageIdAndWidgetId(pageId, widgetId));
  }
}
