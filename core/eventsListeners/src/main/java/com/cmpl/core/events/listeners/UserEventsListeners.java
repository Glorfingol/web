package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.User;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class UserEventsListeners {

  private final ResponsibilityService responsibilityService;

  private final MembershipService membershipService;

  public UserEventsListeners(ResponsibilityService responsibilityService,
    MembershipService membershipService) {
    this.responsibilityService = Objects.requireNonNull(responsibilityService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof User) {
      User deletedUser = (User) deletedEvent.getEntity();
      if (deletedUser != null) {
        responsibilityService.findByUserId(deletedUser.getId())
          .forEach(associationUserRoleDTO -> responsibilityService
            .deleteEntity(associationUserRoleDTO.getId()));

        membershipService.findByGroupId(deletedUser.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }

    }
  }
}
