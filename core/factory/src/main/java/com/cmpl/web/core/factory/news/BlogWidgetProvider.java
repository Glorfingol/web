package com.cmpl.web.core.factory.news;

import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BlogWidgetProvider implements WidgetProviderPlugin {

  private final WebMessageSource messageSource;

  private final ContextHolder contextHolder;

  private final NewsEntryService newsEntryService;

  public BlogWidgetProvider(WebMessageSource messageSource, ContextHolder contextHolder,
    NewsEntryService newsEntryService) {

    this.messageSource = Objects.requireNonNull(messageSource);

    this.newsEntryService = Objects.requireNonNull(newsEntryService);

    this.contextHolder = Objects.requireNonNull(contextHolder);

  }

  @Override
  public Map<String, Object> computeWidgetModel(RenderingWidgetDTO widget, Locale locale,
    int pageNumber, String query) {
    Map<String, Object> widgetModel = new HashMap<>();

    PageWrapper<NewsEntryDTO> pagedNewsWrapped = computePageWrapperOfNews(widget, locale,
      pageNumber);

    List<NewsEntryDTO> entries = computeNewsEntriesForPage(pageNumber, query);
    List<String> entriesIds = entries.stream().map(entry -> String.valueOf(entry.getId()))
      .collect(Collectors.toList());
    widgetModel.put("wrappedNews", pagedNewsWrapped);
    widgetModel.put("news", entriesIds);
    widgetModel.put("widgetId", String.valueOf(widget.getId()));
    widgetModel.put("emptyMessage", getI18nValue("actualites.empty", locale));

    return widgetModel;
  }

  @Override
  public List<? extends BaseDTO> getLinkableEntities() {
    return new ArrayList<>();
  }

  PageWrapper<NewsEntryDTO> computePageWrapperOfNews(RenderingWidgetDTO widget, Locale locale,
    int pageNumber) {
    Page<NewsEntryDTO> pagedNewsEntries = computeNewsEntries(pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    return new PageWrapperBuilder<NewsEntryDTO>().currentPageNumber(currentPageNumber)
      .firstPage(isFirstPage)
      .lastPage(isLastPage).page(pagedNewsEntries).totalPages(totalPages)
      .pageBaseUrl("/widgets/" + widget.getName())
      .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages))
      .build();
  }

  String getI18nValue(String key, Locale locale, Object... args) {
    return messageSource.getI18n(key, locale, args);
  }

  Page<NewsEntryDTO> computeNewsEntries(int pageNumber) {
    List<NewsEntryDTO> newsEntries = new ArrayList<>();
    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    return pagedNewsEntries;
  }

  List<NewsEntryDTO> computeNewsEntriesForPage(int pageNumber, String query) {

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries;
    if (StringUtils.hasText(query)) {
      pagedNewsEntries = newsEntryService.searchEntities(pageRequest, query);
    } else {
      pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    }

    return pagedNewsEntries.getContent();

  }

  @Override
  public String computeWidgetTemplate(RenderingWidgetDTO widget, Locale locale) {
    return "widget_" + widget.getName() + "_" + locale.getLanguage();
  }

  @Override
  public String computeDefaultWidgetTemplate() {
    return "widgets/blog";
  }

  @Override
  public String getWidgetType() {
    return "BLOG";
  }

  @Override
  public String getTooltipKey() {
    return "widget.blog.tooltip";
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
