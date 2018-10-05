package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.service.DefaultBaseService;
import com.cmpl.web.core.models.WidgetPage;
import java.util.List;

public class DefaultWidgetPageService extends
  DefaultBaseService<WidgetPageDTO, WidgetPage> implements
  WidgetPageService {

  private final WidgetPageDAO widgetPageDAO;

  public DefaultWidgetPageService(WidgetPageDAO widgetPageDAO, WidgetPageMapper widgetPageMapper) {
    super(widgetPageDAO, widgetPageMapper);
    this.widgetPageDAO = widgetPageDAO;
  }

  @Override
  public List<WidgetPageDTO> findByPageId(Long pageId) {
    return mapper.toListDTO(widgetPageDAO.findByPageId(pageId));
  }

  @Override
  public List<WidgetPageDTO> findByWidgetId(Long widgetId) {
    return mapper.toListDTO(widgetPageDAO.findByWidgetId(widgetId));
  }

  @Override
  public WidgetPageDTO findByPageIdAndWidgetId(Long pageId, Long widgetId) {
    return mapper.toDTO(widgetPageDAO.findByPageIdAndWidgetId(pageId, widgetId));
  }

  @Override
  public WidgetPageDTO createEntity(WidgetPageDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
