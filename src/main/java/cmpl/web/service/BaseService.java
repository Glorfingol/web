package cmpl.web.service;

import java.util.List;

import cmpl.web.model.news.dto.BaseDTO;

public interface BaseService<T extends BaseDTO> {

  T createEntity(T entity);

  T getEntity(Long id);

  T updateEntity(T entity);

  void deleteEntity(Long id);

  List<T> getEntities();

}
