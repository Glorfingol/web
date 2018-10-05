package com.cmpl.web.core.common.dao;

import com.cmpl.web.core.models.BaseEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BaseDAO<ENTITY extends BaseEntity> {

  /**
   * Creer une entite
   */
  ENTITY createEntity(ENTITY entity);

  /**
   * Recuperer une entite
   */
  ENTITY getEntity(Long id);

  /**
   * Mettre a jour une entite
   */
  ENTITY updateEntity(ENTITY entity);

  /**
   * Supprimer une entite
   */
  void deleteEntity(Long id);

  /**
   * Recuperer toutes les entites
   */
  List<ENTITY> getEntities();

  /**
   * Recuperer une page d'entites
   */
  Page<ENTITY> getPagedEntities(PageRequest pageRequest);

  /**
   * Faire une recherche
   */
  Page<ENTITY> searchEntities(PageRequest pageRequest, String query);

  /**
   * Faire une recherche
   */
  Page<ENTITY> searchLinkedEntities(PageRequest pageRequest, String query, Long linkedToId);

  /**
   * Faire une recherche
   */
  Page<ENTITY> searchNotLinkedEntities(PageRequest pageRequest, String query, Long notLinkedToId);

}
