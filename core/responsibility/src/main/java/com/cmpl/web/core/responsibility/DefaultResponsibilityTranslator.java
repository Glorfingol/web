package com.cmpl.web.core.responsibility;

public class DefaultResponsibilityTranslator implements ResponsibilityTranslator {

  @Override
  public ResponsibilityDTO fromCreateFormToDTO(ResponsibilityCreateForm form) {
    return ResponsibilityDTOBuilder.create().roleId(Long.parseLong(form.getRoleId()))
      .userId(Long.parseLong(form.getUserId()))
      .build();
  }

  @Override
  public ResponsibilityResponse fromDTOToResponse(ResponsibilityDTO dto) {
    return ResponsibilityResponseBuilder.create().associationUserRoleDTO(dto).build();
  }
}
