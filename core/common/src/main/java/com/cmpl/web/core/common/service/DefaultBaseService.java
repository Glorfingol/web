package com.cmpl.web.core.common.service;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.BaseEntity;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Implementation abstraire du service lie aux DAO
 *
 * @author Louis
 */
public class DefaultBaseService<DTO extends BaseDTO, ENTITY extends BaseEntity> implements
  BaseService<DTO> {

  private final BaseDAO<ENTITY> dao;

  protected final BaseMapper<DTO, ENTITY> mapper;

  public DefaultBaseService(BaseDAO<ENTITY> dao, BaseMapper<DTO, ENTITY> mapper) {

    this.dao = Objects.requireNonNull(dao);
    this.mapper = Objects.requireNonNull(mapper);
  }

  @Override
  public DTO createEntity(DTO dto) {
    return mapper.toDTO(dao.createEntity(mapper.toEntity(dto)));

  }

  @Override
  public DTO getEntity(Long id) {
    return mapper.toDTO(dao.getEntity(id));
  }

  @Override
  public DTO updateEntity(DTO dto) {
    return mapper.toDTO(dao.updateEntity(mapper.toEntity(dto)));
  }

  @Override
  public void deleteEntity(Long id) {
    dao.deleteEntity(id);
  }

  @Override
  public List<DTO> getEntities() {
    return dao.getEntities().stream().map(mapper::toDTO).collect(Collectors.toList());
  }

  @Override
  public Page<DTO> getPagedEntities(PageRequest pageRequest) {
    return dao.getPagedEntities(pageRequest).map(mapper::toDTO);
  }

  @Override
  public Page<DTO> searchEntities(PageRequest pageRequest, String query) {
    return dao.searchEntities(pageRequest, query).map(mapper::toDTO);
  }

  @Override
  public Page<DTO> searchLinkedEntities(PageRequest pageRequest, String query, Long linkedToId) {
    return dao.searchLinkedEntities(pageRequest, query, linkedToId).map(mapper::toDTO);
  }

  @Override
  public Page<DTO> searchNotLinkedEntities(PageRequest pageRequest, String query,
    Long notLinkedToId) {
    return dao.searchNotLinkedEntities(pageRequest, query, notLinkedToId).map(mapper::toDTO);
  }

}
