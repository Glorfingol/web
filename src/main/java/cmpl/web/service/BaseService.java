package cmpl.web.service;

import java.util.List;

import cmpl.web.model.news.dto.BaseDTO;

public interface BaseService<T extends BaseDTO> {

  T createEntity(T boUser);

  T getEntity(Long id);

  T updateEntity(T boUser);

  void deleteEntity(Long id);

  List<T> getEntities();

}
