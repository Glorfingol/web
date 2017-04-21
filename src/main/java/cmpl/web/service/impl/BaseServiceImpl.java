package cmpl.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import cmpl.web.filler.impl.ObjectReflexiveFillerImpl;
import cmpl.web.model.news.dao.BaseEntity;
import cmpl.web.model.news.dto.BaseDTO;
import cmpl.web.repository.BaseRepository;
import cmpl.web.service.BaseService;

public abstract class BaseServiceImpl<D extends BaseDTO, E extends BaseEntity> implements BaseService<D> {

  private final BaseRepository<E> entityRepository;

  public BaseServiceImpl(BaseRepository<E> entityRepository) {
    this.entityRepository = entityRepository;
  }

  @Override
  public D createEntity(D dto) {
    dto.setModificationDate(new Date(System.currentTimeMillis()));
    return toDTO(entityRepository.save(toEntity(dto)));
  }

  @Override
  public D getEntity(Long id) {
    E result = entityRepository.findOne(id);
    return result != null ? toDTO(result) : null;
  }

  @Override
  public D updateEntity(D dto) {
    dto.setModificationDate(new Date(System.currentTimeMillis()));
    return toDTO(entityRepository.save(toEntity(dto)));
  }

  @Override
  public void deleteEntity(Long id) {
    entityRepository.delete(id);
  }

  @Override
  public List<D> getEntities() {
    return toListDTO(entityRepository.findAll(new Sort("creationDate")));
  }

  protected List<D> toListDTO(List<E> entities) {
    List<D> dtos = new ArrayList<>();

    for (E entity : entities) {
      dtos.add(toDTO(entity));
    }

    return dtos;
  }

  protected abstract D toDTO(E entity);

  protected abstract E toEntity(D dto);

  protected void fillObject(Object origin, Object destination) {

    ObjectReflexiveFillerImpl reflexiveFiller = ObjectReflexiveFillerImpl.fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
