package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.builder.Builder;

public class AssociationUserRoleResponseBuilder extends Builder<AssociationUserRoleResponse> {

  private AssociationUserRoleDTO associationUserRoleDTO;

  public AssociationUserRoleResponseBuilder associationUserRoleDTO(AssociationUserRoleDTO associationUserRoleDTO) {
    this.associationUserRoleDTO = associationUserRoleDTO;
    return this;
  }

  private AssociationUserRoleResponseBuilder() {

  }

  @Override
  public AssociationUserRoleResponse build() {
    AssociationUserRoleResponse associationUserRoleResponse = new AssociationUserRoleResponse();
    associationUserRoleResponse.setAssociationUserRoleDTO(associationUserRoleDTO);
    return associationUserRoleResponse;
  }

  public static AssociationUserRoleResponseBuilder create() {
    return new AssociationUserRoleResponseBuilder();
  }
}
