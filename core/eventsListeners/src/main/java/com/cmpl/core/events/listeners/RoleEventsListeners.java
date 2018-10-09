package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class RoleEventsListeners {

  private final ResponsibilityService responsibilityService;

  private final MembershipService membershipService;

  private final PrivilegeService privilegeService;

  public RoleEventsListeners(ResponsibilityService responsibilityService,
    PrivilegeService privilegeService, MembershipService membershipService) {
    this.responsibilityService = Objects.requireNonNull(responsibilityService);
    this.privilegeService = Objects.requireNonNull(privilegeService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Role) {
      Role deletedRole = (Role) deletedEvent.getEntity();
      if (deletedRole != null) {
        Long roleId = deletedRole.getId();
        responsibilityService.findByRoleId(roleId)
          .forEach(associationUserRoleDTO -> responsibilityService
            .deleteEntity(associationUserRoleDTO.getId()));
        privilegeService.findByRoleId(roleId)
          .forEach(privilegeDTO -> privilegeService.deleteEntity(privilegeDTO.getId()));

        membershipService.findByGroupId(deletedRole.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }

    }
  }

}
