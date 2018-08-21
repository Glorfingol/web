package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.NewsContent;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class NewsContentDAOImpl extends BaseDAOImpl<NewsContent> implements NewsContentDAO {

  public NewsContentDAOImpl(NewsContentRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(NewsContent.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }
}
