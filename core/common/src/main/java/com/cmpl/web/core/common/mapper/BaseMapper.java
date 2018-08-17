package com.cmpl.web.core.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.filler.ObjectReflexiveFillerImpl;
import com.cmpl.web.core.models.BaseEntity;

public abstract class BaseMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

  public abstract DTO toDTO(ENTITY entity);

  public abstract ENTITY toEntity(DTO dto);

  public List<DTO> toListDTO(List<ENTITY> entities) {
    return entities.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public void fillObject(Object origin, Object destination) {

    ObjectReflexiveFillerImpl reflexiveFiller = ObjectReflexiveFillerImpl.fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
