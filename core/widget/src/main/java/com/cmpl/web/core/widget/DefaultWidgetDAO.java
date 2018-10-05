package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QWidget;
import com.cmpl.web.core.models.QWidgetPage;
import com.cmpl.web.core.models.Widget;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultWidgetDAO extends DefaultBaseDAO<Widget> implements WidgetDAO {

  private final WidgetRepository widgetRepository;

  public DefaultWidgetDAO(WidgetRepository entityRepository, ApplicationEventPublisher publisher) {
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

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QWidget qWidget = QWidget.widget;
    QWidgetPage qWidgetPage = QWidgetPage.widgetPage;
    return qWidget.id.in(new JPAQuery<>().from(qWidgetPage).select(qWidgetPage.widgetId)
      .where(qWidgetPage.pageId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QWidget qWidget = QWidget.widget;
    QWidgetPage qWidgetPage = QWidgetPage.widgetPage;
    return qWidget.id.notIn(new JPAQuery<>().from(qWidgetPage).select(qWidgetPage.widgetId)
      .where(qWidgetPage.pageId.eq(notLinkedToId))
      .distinct());
  }
}
