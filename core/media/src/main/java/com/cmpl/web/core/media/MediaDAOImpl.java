package com.cmpl.web.core.media;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Media;
import com.cmpl.web.core.models.QMedia;
import com.querydsl.core.types.Predicate;

public class MediaDAOImpl extends BaseDAOImpl<Media> implements MediaDAO {

  private final MediaRepository mediaRepository;

  public MediaDAOImpl(MediaRepository entityRepository, ApplicationEventPublisher publisher) {
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
}
