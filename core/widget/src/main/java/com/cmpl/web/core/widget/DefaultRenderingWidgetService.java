package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.service.DefaultReadOnlyService;
import com.cmpl.web.core.models.Widget;
import java.util.Objects;

public class DefaultRenderingWidgetService extends
  DefaultReadOnlyService<RenderingWidgetDTO, Widget> implements RenderingWidgetService {

  private final WidgetDAO widgetDAO;

  public DefaultRenderingWidgetService(WidgetDAO dao, RenderingWidgetMapper mapper) {
    super(dao, mapper);
    this.widgetDAO = Objects.requireNonNull(dao);
  }

  @Override
  public RenderingWidgetDTO findByName(String widgetName) {
    return mapper.toDTO(widgetDAO.findByName(widgetName));
  }
}
