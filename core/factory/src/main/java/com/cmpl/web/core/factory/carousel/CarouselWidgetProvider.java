package com.cmpl.web.core.factory.carousel;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.StringUtils;

public class CarouselWidgetProvider implements WidgetProviderPlugin {

  private final CarouselService carouselService;

  public CarouselWidgetProvider(CarouselService carouselService) {
    this.carouselService = Objects.requireNonNull(carouselService);

  }

  @Override
  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query) {

    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }
    Map<String, Object> widgetModel = new HashMap<>();
    CarouselDTO carousel = carouselService.getEntity(Long.valueOf(widget.getEntityId()));
    widgetModel.put("carousel", carousel);

    return widgetModel;
  }

  @Override
  public List<CarouselDTO> getLinkableEntities() {
    return carouselService.getEntities();
  }

  @Override
  public String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    return "widget_" + widget.getName() + "_" + locale.getLanguage();
  }

  @Override
  public String computeDefaultWidgetTemplate() {
    return "widgets/carousel";
  }

  @Override
  public String getWidgetType() {
    return "CAROUSEL";
  }

  @Override
  public String getTooltipKey() {
    return "widget.carousel.tooltip";
  }

  @Override
  public boolean withDatasource() {
    return true;
  }

  @Override
  public String getAjaxSearchUrl() {
    return "/manager/carousels/ajaxSearch";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
