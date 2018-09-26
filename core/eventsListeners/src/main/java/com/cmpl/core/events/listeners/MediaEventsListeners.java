package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Media;
import java.util.Objects;
import org.springframework.context.event.EventListener;

public class MediaEventsListeners {

  private final FileService fileService;

  private final MembershipService membershipService;

  public MediaEventsListeners(FileService fileService, MembershipService membershipService) {
    this.fileService = Objects.requireNonNull(fileService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Media) {
      Media deletedMedia = (Media) deletedEvent.getEntity();

      if (deletedMedia != null) {
        fileService.removeMediaFromSystem(deletedMedia.getName());
        membershipService.findByGroupId(deletedMedia.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }


    }

  }
}
