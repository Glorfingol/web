package com.cmpl.core.events_listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageService;
import java.util.Objects;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

public class NewsEventsListeners {

  private final NewsContentService newsContentService;

  private final NewsImageService newsImageService;

  private final MembershipService membershipService;

  public NewsEventsListeners(NewsContentService newsContentService,
      NewsImageService newsImageService, MembershipService membershipService) {
    this.newsContentService = Objects.requireNonNull(newsContentService);
    this.newsImageService = Objects.requireNonNull(newsImageService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof NewsEntry) {

      NewsEntry deletedNewsEntry = (NewsEntry) deletedEvent.getEntity();

      if (deletedNewsEntry != null) {
        String contentId = deletedNewsEntry.getContentId();
        if (StringUtils.hasText(contentId)) {
          newsContentService.deleteEntity(Long.parseLong(contentId));
        }
        String imageId = deletedNewsEntry.getImageId();
        if (StringUtils.hasText(imageId)) {
          NewsImageDTO deletedNewsImage = newsImageService.getEntity(Long.parseLong(imageId));
          newsImageService.deleteEntity(deletedNewsImage.getId());
        }
        membershipService.findByGroupId(deletedNewsEntry.getId())
            .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }

    }
  }
}
