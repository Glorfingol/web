package com.cmpl.web.core.association_user_role;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.exception.BaseException;

public class AssociationUserRoleDispatcherImpl implements AssociationUserRoleDispatcher {

  private final AssociationUserRoleService service;

  private final AssociationUserRoleTranslator translator;

  public AssociationUserRoleDispatcherImpl(AssociationUserRoleService service,
      AssociationUserRoleTranslator translator) {

    this.service = Objects.requireNonNull(service);

    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public AssociationUserRoleResponse createEntity(AssociationUserRoleCreateForm createForm, Locale locale)
      throws BaseException {

    AssociationUserRoleDTO associationUserRoleDTOToCreate = translator.fromCreateFormToDTO(createForm);
    AssociationUserRoleDTO createdAssociationUserRoleDTO = service.createEntity(associationUserRoleDTOToCreate);

    return translator.fromDTOToResponse(createdAssociationUserRoleDTO);
  }

  @Override
  public void deleteEntity(String userId, String roleId, Locale locale) throws BaseException {

    AssociationUserRoleDTO associationUserRoleDTO = service.findByUserIdAndRoleId(userId, roleId);
    service.deleteEntity(associationUserRoleDTO.getId());
  }
}
