package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.event.UpdatedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Website;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.sitemap.SitemapService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class WebsiteEventsListeners {

  private final DesignService designService;

  private final SitemapService sitemapService;

  private final PageService pageService;

  private final MembershipService membershipService;

  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  public WebsiteEventsListeners(DesignService designService, SitemapService sitemapService,
    MembershipService membershipService, PageService pageService,
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.pageService = Objects.requireNonNull(pageService);
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);
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

        displayFactoryCacheManager.evictWebsiteByName(deletedWebsite.getName());
        sitemapService.findByWebsiteId(deletedWebsite.getId()).forEach(siteMap
          -> displayFactoryCacheManager.evictPageForHrefAndWebsite(deletedWebsite.getId(),
          pageService.getEntity(siteMap.getPageId()).getHref()));

      }

    }
  }

  @EventListener
  public void handleEntityUpdate(UpdatedEvent updatedEvent) {
    if (updatedEvent.getEntity() instanceof Website) {
      Website website = (Website) updatedEvent.getEntity();
      displayFactoryCacheManager.evictWebsiteByName(website.getName());
    }

  }
}
