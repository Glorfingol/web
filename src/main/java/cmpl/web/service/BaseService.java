package cmpl.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cmpl.web.model.news.dto.BaseDTO;

/**
 * Interface commune aux services lies aux DAO
 * 
 * @author Louis
 *
 * @param <T>
 */
public interface BaseService<T extends BaseDTO> {

  /**
   * Creer une entite
   * 
   * @param entity
   * @return
   */
  T createEntity(T entity);

  /**
   * Recuperer une entite
   * 
   * @param id
   * @return
   */
  T getEntity(Long id);

  /**
   * Mettre a jour une entite
   * 
   * @param entity
   * @return
   */
  T updateEntity(T entity);

  /**
   * Supprimer une entite
   * 
   * @param id
   */
  void deleteEntity(Long id);

  /**
   * Recuperer toutes les entites
   * 
   * @return
   */
  List<T> getEntities();

  /**
   * Recuperer une page d'entites
   * 
   * @param page
   * @return
   */
  Page<T> getPagedEntities(PageRequest pageRequest);

}
