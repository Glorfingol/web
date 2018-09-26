package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Style;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class StyleEventsListeners {

  private final DesignService designService;

  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  private final MembershipService membershipService;

  public StyleEventsListeners(DesignService designService, MembershipService membershipService,
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    this.designService = Objects.requireNonNull(designService);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Style) {
      Style deletedStyle = (Style) deletedEvent.getEntity();
      if (deletedStyle != null) {

        designService.findByStyleId(deletedStyle.getId())
          .forEach(design -> {
            designService.deleteEntity(design.getId());
            displayFactoryCacheManager.evictWebsiteStyles(design.getWebsiteId());
          });

        membershipService.findByGroupId(deletedStyle.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }

    }
  }

}
