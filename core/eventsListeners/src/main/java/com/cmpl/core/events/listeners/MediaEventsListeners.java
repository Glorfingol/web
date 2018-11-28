package com.cmpl.core.events.listeners;

import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Media;
import com.cmpl.web.core.widget.WidgetService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class MediaEventsListeners {

  private final FileService fileService;

  private final MembershipService membershipService;

  private final WidgetService widgetService;

  private final CarouselItemService carouselItemService;

  public MediaEventsListeners(FileService fileService, MembershipService membershipService,
    CarouselItemService carouselItemService, WidgetService widgetService) {
    this.fileService = Objects.requireNonNull(fileService);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.carouselItemService = Objects.requireNonNull(carouselItemService);
    this.widgetService = Objects.requireNonNull(widgetService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Media) {
      Media deletedMedia = (Media) deletedEvent.getEntity();

      if (deletedMedia != null) {
        fileService.removeMediaFromSystem(deletedMedia.getName());
        membershipService.findByGroupId(deletedMedia.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
        carouselItemService.getByMediaId(String.valueOf(deletedMedia.getId()))
          .forEach(
            carouselItem -> carouselItemService.deleteEntityInCarousel(carouselItem.getCarouselId(), carouselItem.getId()));
        widgetService.findByEntityIdAndType(String.valueOf(deletedMedia.getId()), "IMAGE")
          .forEach(widget -> widget.setEntityId(null));
        widgetService.findByEntityIdAndType(String.valueOf(deletedMedia.getId()), "VIDEO")
          .forEach(widget -> widget.setEntityId(null));
      }


    }

  }
}
