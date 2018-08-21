package com.cmpl.web.core.news.image;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.NewsImage;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class NewsImageDAOImpl extends BaseDAOImpl<NewsImage> implements NewsImageDAO {

  public NewsImageDAOImpl(NewsImageRepository entityRepository,
      ApplicationEventPublisher publisher) {
    super(NewsImage.class, entityRepository, publisher);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    throw new UnsupportedOperationException();
  }
}
