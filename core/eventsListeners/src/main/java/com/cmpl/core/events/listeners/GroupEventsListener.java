package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class GroupEventsListener {

  private final ResponsibilityService responsibilityService;

  private final MembershipService membershipService;

  public GroupEventsListener(ResponsibilityService responsibilityService,
    MembershipService membershipService) {
    this.responsibilityService = Objects.requireNonNull(responsibilityService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof BOGroup) {
      BOGroup deletedGroup = (BOGroup) deletedEvent.getEntity();
      if (deletedGroup != null) {
        Long groupId = deletedGroup.getId();
        responsibilityService.findByRoleId(groupId)
          .forEach(
            responsibilityDTO -> responsibilityService.deleteEntity(responsibilityDTO.getId()));
        membershipService.findByGroupId(deletedGroup.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));

      }

    }
  }

}
