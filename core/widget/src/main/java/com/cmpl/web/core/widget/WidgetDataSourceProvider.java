package com.cmpl.web.core.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryService;

public class WidgetDataSourceProvider {

  private final CarouselService carouselService;
  private final MediaService mediaService;
  private final List<String> movieExtensions;
  private final NewsEntryService newsEntryService;

  public WidgetDataSourceProvider(CarouselService carouselService, MediaService mediaService,
      NewsEntryService newsEntryService) {
    this.carouselService = carouselService;
    this.mediaService = mediaService;
    this.movieExtensions = Arrays.asList("avi", "mp4", "flv", "mkv");
    this.newsEntryService = newsEntryService;
  }

  public List<? extends BaseDTO> getLinkableEntities(WIDGET_TYPE type) {
    if (WIDGET_TYPE.CAROUSEL.equals(type)) {
      return getLinkableCarousels();
    }
    if (WIDGET_TYPE.IMAGE.equals(type)) {
      return getLinkableImages();
    }
    if (WIDGET_TYPE.VIDEO.equals(type)) {
      return getLinkableVideos();
    }
    if (WIDGET_TYPE.BLOG_ENTRY.equals(type)) {
      return getLinkableNews();
    }
    return new ArrayList<>();
  }

  List<CarouselDTO> getLinkableCarousels() {
    return carouselService.getEntities();
  }

  List<MediaDTO> getLinkableImages() {
    List<MediaDTO> linkableImages = new ArrayList<>();

    List<MediaDTO> mediaEntities = mediaService.getEntities();
    mediaEntities.forEach(mediaEntity -> {
      if (!movieExtensions.contains(mediaEntity.getExtension())) {
        linkableImages.add(mediaEntity);
      }
    });

    return linkableImages;
  }

  List<MediaDTO> getLinkableVideos() {
    List<MediaDTO> linkableVideos = new ArrayList<>();
    List<MediaDTO> mediaEntities = mediaService.getEntities();
    mediaEntities.forEach(mediaEntity -> {
      if (movieExtensions.contains(mediaEntity.getExtension())) {
        linkableVideos.add(mediaEntity);
      }
    });

    return linkableVideos;
  }

  List<NewsEntryDTO> getLinkableNews() {
    return newsEntryService.getEntities();
  }
}
