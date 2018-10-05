package com.cmpl.web.core.common.dao;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.event.UpdatedEvent;
import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.common.user.GroupGrantedAuthority;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.QBaseEntity;
import com.cmpl.web.core.models.QMembership;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

public abstract class DefaultBaseDAO<ENTITY extends BaseEntity> extends QuerydslRepositorySupport
  implements BaseDAO<ENTITY> {

  private final BaseRepository<ENTITY> entityRepository;

  private final ApplicationEventPublisher publisher;

  private final Class<ENTITY> entityClass;

  public DefaultBaseDAO(Class<ENTITY> domainClass, BaseRepository<ENTITY> entityRepository,
    ApplicationEventPublisher publisher) {
    super(domainClass);

    this.entityClass = domainClass;
    this.entityRepository = Objects.requireNonNull(entityRepository);
    this.publisher = Objects.requireNonNull(publisher);
  }

  @Override
  public ENTITY createEntity(ENTITY entity) {
    entity.setModificationDate(LocalDateTime.now());
    ENTITY createdEntity = entityRepository.save(entity);
    publisher.publishEvent(new UpdatedEvent<>(this, createdEntity));
    return createdEntity;
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
    ENTITY updatedEntity = entityRepository.save(entity);
    publisher.publishEvent(new UpdatedEvent<>(this, updatedEntity));
    return updatedEntity;
  }

  @Override
  public void deleteEntity(Long id) {
    ENTITY deletedEntity = entityRepository.getOne(id);
    publisher.publishEvent(new DeletedEvent<>(this, deletedEntity));
    entityRepository.delete(deletedEntity);
  }

  @Override
  public List<ENTITY> getEntities() {
    Predicate securedPredicate = getSecuredPredicate();
    if (securedPredicate == null) {
      return Lists.newArrayList(entityRepository.findAll(Sort.by(Direction.ASC, "creationDate")));
    }
    return Lists.newArrayList(
      entityRepository.findAll(getSecuredPredicate(), Sort.by(Direction.ASC, "creationDate")));
  }

  @Override
  public Page<ENTITY> getPagedEntities(PageRequest pageRequest) {
    return entityRepository.findAll(getSecuredPredicate(), pageRequest);
  }

  protected Predicate getSecuredPredicate() {
    return getAllPredicate(entityClass);
  }

  private Predicate getAllPredicate(Class entityClass) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return null;
    }
    if ("anonymousUser".equals(auth.getPrincipal())) {
      return null;
    }
    return getDefaultAllPredicate(entityClass, auth, getGroupIds(auth.getAuthorities()));
  }

  private List<Long> getGroupIds(Collection<? extends GrantedAuthority> authorities) {
    List<Long> groupIds = new ArrayList<>();
    if (authorities != null) {
      for (GrantedAuthority grantedAuthority : authorities) {
        if (grantedAuthority instanceof GroupGrantedAuthority) {
          GroupGrantedAuthority groupGrantedAuthority = (GroupGrantedAuthority) grantedAuthority;
          groupIds.add(groupGrantedAuthority.getGroupId());
        }
      }
    }
    return groupIds;
  }

  private Predicate getDefaultAllPredicate(Class entityClass, Authentication auth,
    List<Long> groupIds) {
    QMembership subQ = QMembership.membership;

    String entityPathName = entityClass.getSimpleName().substring(0, 1).toLowerCase()
      + entityClass.getSimpleName().substring(1);

    Path<? extends BaseEntity> entityPath = Expressions.path(entityClass, entityPathName);

    QBaseEntity boEntityPath = new QBaseEntity(entityPath);
    return boEntityPath.creationUser.eq(auth.getName())
      .or(boEntityPath.id
        .in(new JPAQuery<>().from(subQ).select(subQ.entityId).where(subQ.groupId.in(groupIds))
          .distinct()))
      .or(new JPAQuery<>().from(subQ).select(subQ.id).where(subQ.entityId.eq(boEntityPath.id))
        .isNull());
  }

  @Override
  public Page<ENTITY> searchEntities(PageRequest pageRequest, String query) {

    BooleanExpression securedPredicate = (BooleanExpression) getSecuredPredicate();
    Predicate searchPredicate = securedPredicate == null ? computeSearchPredicate(query)
      : securedPredicate.and(computeSearchPredicate(query));

    return entityRepository.findAll(searchPredicate, pageRequest);
  }

  @Override
  public Page<ENTITY> searchLinkedEntities(PageRequest pageRequest, String query, Long linkedToId) {

    Predicate predicate = getSecuredPredicate();
    if (predicate == null) {
      return entityRepository.findAll(pageRequest);
    }
    BooleanExpression booleanPredicate = (BooleanExpression) predicate;
    BooleanExpression linkedToPredicate = (BooleanExpression) computeLinkedPredicate(linkedToId);
    if (linkedToPredicate != null) {
      booleanPredicate = booleanPredicate.and(linkedToPredicate);
    }
    BooleanExpression searchPredicate = null;
    if (StringUtils.hasText(query)) {
      searchPredicate = (BooleanExpression) computeSearchPredicate(query);
    }
    if (searchPredicate != null) {
      booleanPredicate = booleanPredicate.and(searchPredicate);
    }

    return entityRepository.findAll(booleanPredicate, pageRequest);
  }

  @Override
  public Page<ENTITY> searchNotLinkedEntities(PageRequest pageRequest, String query,
    Long linkedToId) {

    Predicate predicate = getSecuredPredicate();
    if (predicate == null) {
      return entityRepository.findAll(pageRequest);
    }
    BooleanExpression booleanPredicate = (BooleanExpression) predicate;
    BooleanExpression notLinkedToPredicate = (BooleanExpression) computeNotLinkedPredicate(
      linkedToId);
    if (notLinkedToPredicate != null) {
      booleanPredicate = booleanPredicate.and(notLinkedToPredicate);
    }
    BooleanExpression searchPredicate = null;
    if (StringUtils.hasText(query)) {
      searchPredicate = (BooleanExpression) computeSearchPredicate(query);
    }
    if (searchPredicate != null) {
      booleanPredicate = booleanPredicate.and(searchPredicate);
    }
    return entityRepository.findAll(booleanPredicate, pageRequest);
  }

  protected abstract Predicate computeSearchPredicate(String query);

  protected abstract Predicate computeLinkedPredicate(Long linkedToId);

  protected abstract Predicate computeNotLinkedPredicate(Long notLinkedToId);


}
