package com.cmpl.web.core.factory.media;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class VideoWidgetProvider implements WidgetProviderPlugin {

  private final MediaService mediaService;
  private final List<String> movieExtensions;

  public VideoWidgetProvider(MediaService mediaService) {

    this.mediaService = Objects.requireNonNull(mediaService);
    this.movieExtensions = Arrays.asList("avi", "mp4", "flv", "mkv");
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }

    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO video = mediaService.getEntity(Long.parseLong(widget.getEntityId()));
    widgetModel.put("mediaUrl", video.getSrc());

    return widgetModel;
  }

  @Override
  public List<MediaDTO> getLinkableEntities() {
    return mediaService.getEntities().stream().filter(mediaDTO -> movieExtensions.contains(mediaDTO.getExtension()))
        .collect(Collectors.toList());
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/video";
  }

  @Override
  public String getWidgetType() {
    return "VIDEO";
  }

  @Override
  public String getTooltipKey() {
    return "widget.video.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
