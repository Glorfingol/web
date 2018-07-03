package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.Media;

public class MediaEventsListeners {

  private final FileService fileService;

  public MediaEventsListeners(FileService fileService) {
    this.fileService = fileService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (MediaDTO.class.equals(clazz)) {
      Media deletedMedia = (Media) deletedEvent.getEntity();

      if (deletedMedia != null) {
        fileService.removeMediaFromSystem(deletedMedia.getName());
      }
    }

  }
}
