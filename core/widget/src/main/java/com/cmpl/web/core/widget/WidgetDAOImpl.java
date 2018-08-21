package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.QWidget;
import com.cmpl.web.core.models.Widget;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class WidgetDAOImpl extends BaseDAOImpl<Widget> implements WidgetDAO {

  private final WidgetRepository widgetRepository;

  public WidgetDAOImpl(WidgetRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Widget.class, entityRepository, publisher);
    this.widgetRepository = entityRepository;
  }

  @Override
  public Widget findByName(String widgetName) {
    return widgetRepository.findByName(widgetName);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QWidget qWidget = QWidget.widget;
    return qWidget.name.containsIgnoreCase(query).or(qWidget.type.containsIgnoreCase(query));
  }
}
