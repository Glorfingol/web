package com.cmpl.web.core.factory.news;

import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.StringUtils;

public class BlogEntryWidgetProvider implements WidgetProviderPlugin {

  private final NewsEntryService newsEntryService;

  public BlogEntryWidgetProvider(NewsEntryService newsEntryService) {

    this.newsEntryService = Objects.requireNonNull(newsEntryService);

  }

  @Override
  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query) {
    Map<String, Object> widgetModel = new HashMap<>();

    String newsEntryId = widget.getEntityId();
    if (StringUtils.hasText(newsEntryId)) {
      NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));
      widgetModel.put("newsBean", newsEntry);
    }

    return widgetModel;
  }

  @Override
  public List<NewsEntryDTO> getLinkableEntities() {
    return newsEntryService.getEntities();

  }

  @Override
  public String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    return "widget_" + widget.getName() + "_" + locale.getLanguage();
  }

  @Override
  public String computeDefaultWidgetTemplate() {
    return "widgets/blog_entry";
  }

  @Override
  public String getWidgetType() {
    return "BLOG_ENTRY";
  }

  @Override
  public String getTooltipKey() {
    return "widget.blog.entry.tooltip";
  }

  @Override
  public boolean withDatasource() {
    return true;
  }

  @Override
  public String getAjaxSearchUrl() {
    return "/manager/news/ajaxSearch";
  }


  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
