package com.cmpl.web.core.style;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QStyle;
import com.cmpl.web.core.models.Style;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultStyleDAO extends DefaultBaseDAO<Style> implements StyleDAO {

  private final StyleRepository styleRepository;

  public DefaultStyleDAO(StyleRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Style.class, entityRepository, publisher);
    this.styleRepository = entityRepository;
  }

  @Override
  public Style getStyle() {
    return styleRepository.findAll().get(0);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QStyle qStyle = QStyle.style;
    return qStyle.name.containsIgnoreCase(query);
  }
}