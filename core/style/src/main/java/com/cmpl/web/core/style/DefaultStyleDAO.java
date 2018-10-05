package com.cmpl.web.core.style;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.QDesign;
import com.cmpl.web.core.models.QStyle;
import com.cmpl.web.core.models.Style;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
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

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    QStyle qStyle = QStyle.style;
    QDesign qDesign = QDesign.design;
    return qStyle.id.in(new JPAQuery<>().from(qDesign).select(qDesign.styleId)
      .where(qDesign.websiteId.eq(linkedToId))
      .distinct());
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    QStyle qStyle = QStyle.style;
    QDesign qDesign = QDesign.design;
    return qStyle.id.notIn(new JPAQuery<>().from(qDesign).select(qDesign.styleId)
      .where(qDesign.websiteId.eq(notLinkedToId))
      .distinct());
  }
}
