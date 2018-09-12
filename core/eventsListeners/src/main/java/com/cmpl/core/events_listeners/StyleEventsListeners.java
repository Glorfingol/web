package com.cmpl.core.events_listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Style;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class StyleEventsListeners {

  private final DesignService designService;

  private final MembershipService membershipService;

  public StyleEventsListeners(DesignService designService, MembershipService membershipService) {
    this.designService = Objects.requireNonNull(designService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Style) {
      Style deletedStyle = (Style) deletedEvent.getEntity();
      if (deletedStyle != null) {

        designService.findByStyleId(deletedStyle.getId())
            .forEach(design -> designService.deleteEntity(design.getId()));

        membershipService.findByGroupId(deletedStyle.getId())
            .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));

      }

    }
  }

}
