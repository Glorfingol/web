package com.cmpl.web.core.common.mapper;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.filler.DefaultObjectReflexiveFiller;
import com.cmpl.web.core.models.BaseEntity;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

  public abstract DTO toDTO(ENTITY entity);

  public abstract ENTITY toEntity(DTO dto);

  public List<DTO> toListDTO(List<ENTITY> entities) {
    return entities.stream().map(this::toDTO).collect(Collectors.toList());
  }

  public void fillObject(Object origin, Object destination) {

    DefaultObjectReflexiveFiller reflexiveFiller = DefaultObjectReflexiveFiller
        .fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
