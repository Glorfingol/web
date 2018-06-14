package com.cmpl.web.core.widget.page;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cmpl.web.core.common.service.BaseServiceImpl;

@CacheConfig(cacheNames = "widgetPages")
public class WidgetPageServiceImpl extends BaseServiceImpl<WidgetPageDTO, WidgetPage> implements WidgetPageService {

  private final WidgetPageDAO widgetPageDAO;

  public WidgetPageServiceImpl(WidgetPageDAO widgetPageDAO, WidgetPageMapper widgetPageMapper) {
    super(widgetPageDAO, widgetPageMapper);
    this.widgetPageDAO = widgetPageDAO;
  }

  @Override
  @Cacheable(value = "forPage", key = "#a0")
  public List<WidgetPageDTO> findByPageId(String pageId) {
    return mapper.toListDTO(widgetPageDAO.findByPageId(pageId));
  }

  @Override
  @Cacheable(value = "forWidget", key = "#a0")
  public List<WidgetPageDTO> findByWidgetId(String widgetId) {
    return mapper.toListDTO(widgetPageDAO.findByWidgetId(widgetId));
  }

  @Override
  @Cacheable(value = "byPageAndWidget", key = "#a0+'_'+#a1")
  public WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId) {
    return mapper.toDTO(widgetPageDAO.findByPageIdAndWidgetId(pageId, widgetId));
  }

  @Override
  @CacheEvict(value = {"forWidget", "forPage", "byPageAndWidget"}, allEntries = true)
  public WidgetPageDTO createEntity(WidgetPageDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @CacheEvict(value = {"forWidget", "forPage", "byPageAndWidget"}, allEntries = true)
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
