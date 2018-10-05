package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.NewsContent;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultNewsContentDAO extends DefaultBaseDAO<NewsContent> implements NewsContentDAO {

  public DefaultNewsContentDAO(NewsContentRepository entityRepository,
    ApplicationEventPublisher publisher) {
    super(NewsContent.class, entityRepository, publisher);
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
