package com.cmpl.web.core.group;

import com.cmpl.web.core.common.mapper.BaseMapper;

public class GroupMapper extends BaseMapper<GroupDTO, Group> {

  @Override
  public GroupDTO toDTO(Group entity) {
    GroupDTO dto = GroupDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Group toEntity(GroupDTO dto) {
    Group entity = GroupBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
