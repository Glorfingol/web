package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.WidgetPage;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultWidgetPageDAO extends DefaultBaseDAO<WidgetPage> implements WidgetPageDAO {

  private final WidgetPageRepository widgetPageRepository;

  public DefaultWidgetPageDAO(WidgetPageRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(WidgetPage.class, entityRepository, publisher);
    this.widgetPageRepository = entityRepository;
  }

  @Override
  public List<WidgetPage> findByPageId(String pageId) {
    return widgetPageRepository.findByPageId(pageId);
  }

  @Override
  public List<WidgetPage> findByWidgetId(String widgetId) {
    return widgetPageRepository.findByWidgetId(widgetId);
  }

  @Override
  public WidgetPage findByPageIdAndWidgetId(String pageId, String widgetId) {
    return widgetPageRepository.findByPageIdAndWidgetId(pageId, widgetId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }
}
