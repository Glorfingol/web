package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class ResponsibilityDTOBuilder extends BaseBuilder<ResponsibilityDTO> {

  private Long userId;

  private Long roleId;

  public ResponsibilityDTOBuilder userId(Long userId) {
    this.userId = userId;
    return this;
  }

  public ResponsibilityDTOBuilder roleId(Long roleId) {
    this.roleId = roleId;
    return this;
  }

  private ResponsibilityDTOBuilder() {

  }

  @Override
  public ResponsibilityDTO build() {
    ResponsibilityDTO responsibilityDTO = new ResponsibilityDTO();
    responsibilityDTO.setRoleId(roleId);
    responsibilityDTO.setUserId(userId);
    responsibilityDTO.setCreationDate(creationDate);
    responsibilityDTO.setCreationUser(creationUser);
    responsibilityDTO.setModificationUser(modificationUser);
    responsibilityDTO.setId(id);
    responsibilityDTO.setModificationDate(modificationDate);
    return responsibilityDTO;
  }

  public static ResponsibilityDTOBuilder create() {
    return new ResponsibilityDTOBuilder();
  }
}
