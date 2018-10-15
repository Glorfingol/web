package com.cmpl.web.core.provider;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

@Qualifier(value = "widgetProviders")
public interface WidgetProviderPlugin extends Plugin<String> {

  Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query);

  List<? extends BaseDTO> getLinkableEntities();

  String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale);

  String computeDefaultWidgetTemplate();

  String getWidgetType();

  String getTooltipKey();

  boolean withDatasource();

  String getAjaxSearchUrl();

}
