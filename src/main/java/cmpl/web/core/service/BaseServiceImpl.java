package cmpl.web.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import cmpl.web.core.filler.ObjectReflexiveFillerImpl;
import cmpl.web.core.model.BaseDTO;
import cmpl.web.core.model.BaseEntity;
import cmpl.web.core.repository.BaseRepository;

/**
 * Implementation abstraire du service lie aux DAO
 * 
 * @author Louis
 *
 * @param <D>
 * @param <E>
 */
public abstract class BaseServiceImpl<D extends BaseDTO, E extends BaseEntity> implements BaseService<D> {

  private final BaseRepository<E> entityRepository;

  /**
   * Constructeur a appeler via super
   * 
   * @param entityRepository
   */
  public BaseServiceImpl(BaseRepository<E> entityRepository) {
    this.entityRepository = entityRepository;
  }

  @Override
  public D createEntity(D dto) {
    dto.setModificationDate(LocalDate.now());
    return toDTO(entityRepository.save(toEntity(dto)));
  }

  @Override
  public D getEntity(Long id) {
    E result = entityRepository.findOne(id);
    return result != null ? toDTO(result) : null;
  }

  @Override
  public D updateEntity(D dto) {
    dto.setModificationDate(LocalDate.now());
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

  @Override
  public Page<D> getPagedEntities(PageRequest pageRequest) {
    return toPageDTO(entityRepository.findAll(pageRequest), pageRequest);
  }

  protected Page<D> toPageDTO(Page<E> pagedEntities, PageRequest pageRequest) {

    List<D> dtos = new ArrayList<>();

    for (E entity : pagedEntities.getContent()) {
      dtos.add(toDTO(entity));
    }

    return new PageImpl<>(dtos, pageRequest, pagedEntities.getTotalElements());
  }

  public List<D> toListDTO(List<E> entities) {
    List<D> dtos = new ArrayList<>();

    for (E entity : entities) {
      dtos.add(toDTO(entity));
    }

    return dtos;
  }

  protected abstract D toDTO(E entity);

  protected abstract E toEntity(D dto);

  public void fillObject(Object origin, Object destination) {

    ObjectReflexiveFillerImpl reflexiveFiller = ObjectReflexiveFillerImpl.fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
