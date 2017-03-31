package cmpl.web.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import cmpl.web.model.news.METHOD;
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
    List<D> dtos = new ArrayList<D>();

    for (E entity : entities) {
      dtos.add(toDTO(entity));
    }

    return dtos;
  }

  protected abstract D toDTO(E entity);

  protected abstract E toEntity(D dto);

  protected void fillObject(Object origin, Object destination) {

    List<Field> destinationFields = getFields(destination.getClass());
    List<Field> originFields = getFields(origin.getClass());

    try {

      for (Field destinationField : destinationFields) {

        Method setterDestination = destination.getClass().getMethod(getSetterName(destinationField),
            destinationField.getType());

        for (Field originField : originFields) {
          if (originField.getName().equals(destinationField.getName())) {

            Method getterOrigin = origin.getClass().getMethod(getGetterName(originField));

            setterDestination.invoke(destination, getterOrigin.invoke(origin));

          }
        }
      }
    } catch (Exception e) {
    }

  }

  private List<Field> getFields(Class<?> classObject) {

    List<Field> fields = new ArrayList<>();

    List<Field> classFields = Arrays.asList(classObject.getDeclaredFields());
    fields.addAll(classFields);

    if (classObject.getSuperclass() != null) {
      fields.addAll(getFields(classObject.getSuperclass()));
    }

    return fields;

  }

  protected String getSetterName(Field field) {
    return getMethodName(METHOD.SETTER, field);
  }

  protected String getGetterName(Field field) {
    if (boolean.class.equals(field.getType())) {
      return getMethodName(METHOD.BOOLEAN_GETTER, field);
    } else {
      return getMethodName(METHOD.GETTER, field);
    }

  }

  String getMethodName(METHOD method, Field field) {
    StringBuilder sb = new StringBuilder();
    sb.append(method.getPrefix());

    sb.append(field.getName().replaceFirst(field.getName().substring(0, 1),
        field.getName().substring(0, 1).toUpperCase()));

    return sb.toString();
  }

}
