package com.cmpl.web.core.news.content;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;

public class NewsContentDAOImpl extends BaseDAOImpl<NewsContent> implements NewsContentDAO {

  public NewsContentDAOImpl(NewsContentRepository entityRepository, ApplicationEventPublisher publisher) {
    super(NewsContent.class, entityRepository, publisher);
  }
}
