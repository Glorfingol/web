package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageService;

public class NewsEventsListeners {

  private final NewsContentService newsContentService;
  private final NewsImageService newsImageService;
  private final FileService fileService;

  public NewsEventsListeners(NewsContentService newsContentService, NewsImageService newsImageService,
      FileService fileService) {
    this.newsContentService = newsContentService;
    this.fileService = fileService;
    this.newsImageService = newsImageService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (NewsEntryDTO.class.equals(clazz)) {

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
          MediaDTO deletedNewsMedia = deletedNewsImage.getMedia();
          if (deletedNewsMedia != null) {
            fileService.removeMediaFromSystem(deletedNewsMedia.getName());
          }
        }
      }

    }
  }
}
