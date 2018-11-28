package com.cmpl.core.events.listeners;

import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.models.Carousel;
import com.cmpl.web.core.widget.WidgetService;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class CarouselEventsListener {


  private final WidgetService widgetService;

  private final CarouselItemService carouselItemService;

  public CarouselEventsListener(CarouselItemService carouselItemService, WidgetService widgetService) {
    this.carouselItemService = Objects.requireNonNull(carouselItemService);
    this.widgetService = Objects.requireNonNull(widgetService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Carousel) {
      Carousel deletedCarousel = (Carousel) deletedEvent.getEntity();
      if (deletedCarousel != null) {
        carouselItemService.getByCarouselId(String.valueOf(deletedCarousel.getId()))
          .forEach(
            carouselItem -> carouselItemService.deleteEntityInCarousel(carouselItem.getCarouselId(), carouselItem.getId()));
        widgetService.findByEntityIdAndType(String.valueOf(deletedCarousel.getId()), "CAROUSEL")
          .forEach(widget -> widget.setEntityId(null));
      }


    }

  }

}
