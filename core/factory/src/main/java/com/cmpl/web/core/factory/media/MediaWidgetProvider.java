package com.cmpl.web.core.factory.media;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

public abstract class MediaWidgetProvider {


  protected Map<String, Object> computeMediaWidgetModel(RenderingWidgetDTO widget) {
    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }

    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO image = recoverMedia(widget);
    widgetModel.put("mediaUrl", image.getSrc());

    return widgetModel;
  }

  protected abstract MediaDTO recoverMedia(RenderingWidgetDTO widget);

}
