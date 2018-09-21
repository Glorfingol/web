package com.cmpl.web.core.common.repository;

import com.cmpl.web.core.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Interface commune de repository
 *
 * @author Louis
 */
public interface BaseRepository<T extends BaseEntity> extends QuerydslPredicateExecutor<T>,
    JpaRepository<T, Long> {

}
