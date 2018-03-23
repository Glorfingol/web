package com.cmpl.web.core.role;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public class RoleDispatcherImpl implements RoleDispatcher {

  private final RoleValidator validator;
  private final RoleTranslator translator;
  private final RoleService service;

  public RoleDispatcherImpl(RoleService service, RoleValidator validator, RoleTranslator translator) {
    this.validator = validator;
    this.service = service;
    this.translator = translator;
  }

  @Override
  public RoleResponse createEntity(RoleCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return RoleResponseBuilder.create().error(error).build();
    }

    RoleDTO roleToCreate = translator.fromCreateFormToDTO(form);
    RoleDTO createdRole = service.createEntity(roleToCreate);
    return translator.fromDTOToResponse(createdRole);
  }

  @Override
  public RoleResponse updateEntity(RoleUpdateForm form, Locale locale) {
    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return RoleResponseBuilder.create().error(error).build();
    }

    RoleDTO roleToUpdate = service.getEntity(form.getId());
    roleToUpdate.setDescription(form.getDescription());
    roleToUpdate.setName(form.getName());

    RoleDTO roleUpdated = service.updateEntity(roleToUpdate);
    return translator.fromDTOToResponse(roleUpdated);
  }
}
