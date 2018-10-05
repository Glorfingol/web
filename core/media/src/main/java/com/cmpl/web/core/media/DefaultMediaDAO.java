package com.cmpl.web.core.media;

import com.cmpl.web.core.common.dao.DefaultBaseDAO;
import com.cmpl.web.core.models.Media;
import com.cmpl.web.core.models.QMedia;
import com.querydsl.core.types.Predicate;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultMediaDAO extends DefaultBaseDAO<Media> implements MediaDAO {

  private final MediaRepository mediaRepository;

  public DefaultMediaDAO(MediaRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Media.class, entityRepository, publisher);
    this.mediaRepository = entityRepository;
  }

  @Override
  public Media findByName(String name) {
    return mediaRepository.findByName(name);
  }

  @Override
  protected Predicate computeSearchPredicate(String query) {
    QMedia qMedia = QMedia.media;
    return qMedia.name.containsIgnoreCase(query).or(qMedia.extension.containsIgnoreCase(query));
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
