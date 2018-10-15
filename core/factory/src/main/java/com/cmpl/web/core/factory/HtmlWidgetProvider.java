package com.cmpl.web.core.factory;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HtmlWidgetProvider implements WidgetProviderPlugin {

  @Override
  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query) {
    return new HashMap<>();
  }

  @Override
  public List<? extends BaseDTO> getLinkableEntities() {
    return new ArrayList<>();
  }

  @Override
  public String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    return "widget_" + widget.getName() + "_" + locale.getLanguage();
  }

  @Override
  public String computeDefaultWidgetTemplate() {
    return "widgets/default";
  }

  @Override
  public String getWidgetType() {
    return "HTML";
  }

  @Override
  public String getTooltipKey() {
    return "widget.default.tooltip";
  }

  @Override
  public boolean withDatasource() {
    return false;
  }

  @Override
  public String getAjaxSearchUrl() {
    return null;
  }


  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
