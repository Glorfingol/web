package com.cmpl.web.core.news.entry;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.models.QNewsEntry;
import com.querydsl.core.types.Predicate;

public class NewsEntryDAOImpl extends BaseDAOImpl<NewsEntry> implements NewsEntryDAO {

  private final NewsEntryRepository newsEntryRepository;

  public NewsEntryDAOImpl(NewsEntryRepository entityRepository, ApplicationEventPublisher publisher) {
    super(NewsEntry.class, entityRepository, publisher);
    this.newsEntryRepository = entityRepository;
  }

  @Override
  public List<NewsEntry> findByFacebookId(String facebookId) {
    return newsEntryRepository.findByFacebookId(facebookId);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QNewsEntry entry = QNewsEntry.newsEntry;
    return entry.tags.containsIgnoreCase(query).or(entry.title.containsIgnoreCase(query))
        .or(entry.author.containsIgnoreCase(query));
  }
}
