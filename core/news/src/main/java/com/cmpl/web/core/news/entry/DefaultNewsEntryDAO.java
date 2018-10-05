package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.models.QNewsEntry;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultNewsEntryDAO extends DefaultBaseDAO<NewsEntry> implements NewsEntryDAO {

  private final NewsEntryRepository newsEntryRepository;

  public DefaultNewsEntryDAO(NewsEntryRepository entityRepository,
    ApplicationEventPublisher publisher) {
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

  @Override
  protected Predicate computeLinkedPredicate(Long linkedToId) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Predicate computeNotLinkedPredicate(Long notLinkedToId) {
    throw new UnsupportedOperationException();
  }
}
