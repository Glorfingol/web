package com.cmpl.web.core.news.image;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.NewsImage;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultNewsImageDAO extends DefaultBaseDAO<NewsImage> implements NewsImageDAO {

  public DefaultNewsImageDAO(NewsImageRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(NewsImage.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    throw new UnsupportedOperationException();
  }
}
