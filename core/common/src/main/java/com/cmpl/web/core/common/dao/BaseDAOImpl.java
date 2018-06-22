package com.cmpl.web.core.common.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.repository.BaseRepository;

public class BaseDAOImpl<ENTITY extends BaseEntity> extends QuerydslRepositorySupport implements BaseDAO<ENTITY> {

  private final BaseRepository<ENTITY> entityRepository;
  private final ApplicationEventPublisher publisher;

  public BaseDAOImpl(Class<?> domainClass, BaseRepository<ENTITY> entityRepository,
      ApplicationEventPublisher publisher) {
    super(domainClass);

    this.entityRepository = Objects.requireNonNull(entityRepository);
    this.publisher = Objects.requireNonNull(publisher);
  }

  @Override
  public ENTITY createEntity(ENTITY entity) {
    entity.setModificationDate(LocalDateTime.now());
    return entityRepository.save(entity);
  }

  @Override
  public ENTITY getEntity(Long id) {
    Optional<ENTITY> result = entityRepository.findById(id);
    if (result == null || !result.isPresent()) {
      return null;
    }
    return result.get();
  }

  @Override
  public ENTITY updateEntity(ENTITY entity) {
    entity.setModificationDate(LocalDateTime.now());
    return entityRepository.save(entity);
  }

  @Override
  public void deleteEntity(Long id) {
    ENTITY deletedEntity = entityRepository.getOne(id);
    publisher.publishEvent(new DeletedEvent<ENTITY>(this, deletedEntity));
    entityRepository.delete(deletedEntity);
  }

  @Override
  public List<ENTITY> getEntities() {
    return entityRepository.findAll(new Sort(Direction.ASC, "creationDate"));
  }

  @Override
  public Page<ENTITY> getPagedEntities(PageRequest pageRequest) {
    return entityRepository.findAll(pageRequest);
  }

  @Override
  public Page<ENTITY> searchEntities(PageRequest pageRequest, String query) {
    return null;
  }
}
