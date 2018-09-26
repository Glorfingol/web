package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.CreatedEvent;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.models.WidgetPage;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class WidgetPageEventsListeners {

  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  public WidgetPageEventsListeners(
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {
    if (deletedEvent.getEntity() instanceof WidgetPage) {
      WidgetPage widgetPage = (WidgetPage) deletedEvent.getEntity();
      displayFactoryCacheManager.evictWidgetsIdsForPage(widgetPage.getId());
    }

  }

  @EventListener
  public void handleEntityCreation(CreatedEvent createdEvent) {
    if (createdEvent.getEntity() instanceof WidgetPage) {
      WidgetPage widgetPage = (WidgetPage) createdEvent.getEntity();
      displayFactoryCacheManager.evictWidgetsIdsForPage(widgetPage.getId());
    }
  }

}
