package com.cmpl.web.core.widget;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;

@CacheConfig(cacheNames = "widgets")
public class WidgetServiceImpl extends BaseServiceImpl<WidgetDTO, Widget> implements WidgetService {

  private final FileService fileService;
  private final WidgetRepository repository;
  private static final String WIDGET_PREFIX = "widget_";
  private static final String WIDGET_SUFFIX = ".html";

  public WidgetServiceImpl(WidgetRepository repository, FileService fileService) {
    super(repository);
    this.fileService = fileService;
    this.repository = repository;
  }

  @Override
  @Transactional
  @CacheEvict(value = "pagedWidgets", allEntries = true)
  public WidgetDTO createEntity(WidgetDTO dto) {
    WidgetDTO updatedWidget = super.createEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + WIDGET_SUFFIX, dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  @Transactional
  @CachePut(key = "#a0.id")
  @CacheEvict(value = {"pagedWidgets"}, allEntries = true)
  public WidgetDTO updateEntity(WidgetDTO dto) {
    WidgetDTO updatedWidget = super.updateEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + WIDGET_SUFFIX, dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  @Cacheable(key = "#a0")
  public WidgetDTO getEntity(Long widgetId) {
    WidgetDTO fetchedWidget = super.getEntity(widgetId);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(WIDGET_PREFIX + fetchedWidget.getName()
        + WIDGET_SUFFIX));
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
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @Cacheable(key = "#a0")
  public WidgetDTO findByName(String widgetName) {
    return toDTO(repository.findByName(widgetName));
  }

  @Override
  @Cacheable(value = "pagedWidgets")
  public Page<WidgetDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }
}
