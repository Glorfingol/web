package com.cmpl.web.core.common.service;

import com.cmpl.web.core.common.dto.BaseDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Interface commune aux services lies aux DAO
 *
 * @author Louis
 */
public interface BaseService<DTO extends BaseDTO> {

  /**
   * Creer une entite
   */
  DTO createEntity(DTO entity);

  /**
   * Recuperer une entite
   */
  DTO getEntity(Long id);

  /**
   * Mettre a jour une entite
   */
  DTO updateEntity(DTO entity);

  /**
   * Supprimer une entite
   */
  void deleteEntity(Long id);

  /**
   * Recuperer toutes les entites
   */
  List<DTO> getEntities();

  /**
   * Recuperer une page d'entites
   */
  Page<DTO> getPagedEntities(PageRequest pageRequest);

  /**
   * Faire une recherche
   */
  Page<DTO> searchEntities(PageRequest pageRequest, String query);

  /**
   * Faire une recherche
   */
  Page<DTO> searchLinkedEntities(PageRequest pageRequest, String query, Long linkedToId);

  /**
   * Faire une recherche
   */
  Page<DTO> searchNotLinkedEntities(PageRequest pageRequest, String query, Long notLinkedToId);

}
