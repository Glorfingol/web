package com.cmpl.web.core.factory.media;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImageWidgetProvider extends MediaWidgetProvider implements WidgetProviderPlugin {

  private final MediaService mediaService;

  private final List<String> movieExtensions;

  public ImageWidgetProvider(MediaService mediaService) {

    this.mediaService = Objects.requireNonNull(mediaService);
    this.movieExtensions = Arrays.asList("avi", "mp4", "flv", "mkv");
  }

  @Override
  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query) {
    return computeMediaWidgetModel(widget);
  }

  @Override
  public List<MediaDTO> getLinkableEntities() {
    return mediaService.getEntities().stream()
      .filter(mediaDTO -> !movieExtensions.contains(mediaDTO.getExtension()))
      .collect(Collectors.toList());
  }

  @Override
  public String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    return "widget_" + widget.getName() + "_" + locale.getLanguage();
  }

  @Override
  public String computeDefaultWidgetTemplate() {
    return "widgets/image";
  }

  @Override
  public String getWidgetType() {
    return "IMAGE";
  }

  @Override
  public String getTooltipKey() {
    return "widget.image.tooltip";
  }

  @Override
  public boolean withDatasource() {
    return true;
  }

  @Override
  public String getAjaxSearchUrl() {
    return "/manager/medias/ajaxSearch";
  }


  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }

  @Override
  protected MediaDTO recoverMedia(RenderingWidgetDTO widget) {
    return mediaService.getEntity(Long.parseLong(widget.getEntityId()));

  }

}
