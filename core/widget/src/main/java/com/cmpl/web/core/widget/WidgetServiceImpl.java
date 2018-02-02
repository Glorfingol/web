package com.cmpl.web.core.widget;

import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;

public class WidgetServiceImpl extends BaseServiceImpl<WidgetDTO,Widget> implements WidgetService{


  private final FileService fileService;
  private static final String WIDGET_PREFIX = "widget_";
  private static final String WIDGET_SUFFIX = ".html";

  public WidgetServiceImpl(WidgetRepository repository,FileService fileService) {
    super(repository);
    this.fileService = fileService;
  }



  @Override
  @Transactional
  public WidgetDTO updateEntity(WidgetDTO dto) {
    WidgetDTO updatedWidget = super.updateEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX +dto.getName() + WIDGET_SUFFIX, dto.getPersonalization());

    return updatedWidget;
  }


  @Override
  public WidgetDTO getEntity(Long widgetId) {
    WidgetDTO fetchedWidget = super.getEntity(widgetId);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(WIDGET_PREFIX +fetchedWidget.getName() + WIDGET_SUFFIX));
    return fetchedWidget;
  }

  @Override
  protected WidgetDTO toDTO(Widget entity) {
    WidgetDTO dto = WidgetDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Widget toEntity(WidgetDTO dto) {
    Widget entity = WidgetBuilder.create().build();
    fillObject(dto,entity);
    return entity;
  }
}
