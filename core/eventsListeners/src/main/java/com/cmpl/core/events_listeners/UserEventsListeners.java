package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.common.dao.BaseEntity;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.user.UserDTO;

public class UserEventsListeners {

  private final AssociationUserRoleService associationUserRoleService;

  public UserEventsListeners(AssociationUserRoleService associationUserRoleService) {
    this.associationUserRoleService = associationUserRoleService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (UserDTO.class.equals(clazz)) {
      User deletedUser = (User) deletedEvent.getEntity();
      if (deletedUser != null) {
        associationUserRoleService.findByUserId(String.valueOf(deletedUser.getId()))
            .forEach(associationUserRoleDTO -> associationUserRoleService.deleteEntity(associationUserRoleDTO.getId()));
      }

    }
  }
}
