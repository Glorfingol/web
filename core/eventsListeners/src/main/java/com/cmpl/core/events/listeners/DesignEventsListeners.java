package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.CreatedEvent;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.models.Design;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class DesignEventsListeners {

  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  public DesignEventsListeners(DisplayFactoryCacheManager displayFactoryCacheManager) {
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {
    if (deletedEvent.getEntity() instanceof Design) {
      Design design = (Design) deletedEvent.getEntity();
      displayFactoryCacheManager.evictWebsiteStyles(design.getWebsiteId());
    }

  }

  @EventListener
  public void handleEntityCreation(CreatedEvent createdEvent) {
    if (createdEvent.getEntity() instanceof Design) {
      Design design = (Design) createdEvent.getEntity();
      displayFactoryCacheManager.evictWebsiteStyles(design.getWebsiteId());
    }
  }


}
