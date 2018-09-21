package com.cmpl.core.events_listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Website;
import com.cmpl.web.core.sitemap.SitemapService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class WebsiteEventsListeners {

  private final DesignService designService;

  private final SitemapService sitemapService;

  private final MembershipService membershipService;

  public WebsiteEventsListeners(DesignService designService, SitemapService sitemapService,
      MembershipService membershipService) {
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Website) {
      Website deletedWebsite = (Website) deletedEvent.getEntity();
      if (deletedWebsite != null) {

        designService.findByWebsiteId(deletedWebsite.getId())
            .forEach(design -> designService.deleteEntity(design.getId()));
        sitemapService.findByWebsiteId(deletedWebsite.getId())
            .forEach(design -> sitemapService.deleteEntity(design.getId()));

        membershipService.findByGroupId(deletedWebsite.getId())
            .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));

      }

    }
  }
}
