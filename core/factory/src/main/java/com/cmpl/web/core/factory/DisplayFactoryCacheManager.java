package com.cmpl.web.core.factory;

import com.cmpl.web.core.design.DesignDTO;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.RenderingPageDTO;
import com.cmpl.web.core.page.RenderingPageService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.RenderingWidgetDTO;
import com.cmpl.web.core.widget.RenderingWidgetService;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class DisplayFactoryCacheManager {

  private final RenderingPageService renderingPageService;

  private final RenderingWidgetService renderingWidgetService;

  private final NewsEntryService newsEntryService;

  private final WidgetPageService widgetPageService;

  private final WebsiteService websiteService;

  private final SitemapService sitemapService;

  private final DesignService designService;

  private final StyleService styleService;

  public DisplayFactoryCacheManager(
    RenderingPageService renderingPageService,
    NewsEntryService newsEntryService, WidgetPageService widgetPageService,
    RenderingWidgetService renderingWidgetService,
    WebsiteService websiteService,
    SitemapService sitemapService, DesignService designService, StyleService styleService) {

    this.renderingPageService = Objects.requireNonNull(renderingPageService);
    this.newsEntryService = Objects.requireNonNull(newsEntryService);
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.renderingWidgetService = Objects.requireNonNull(renderingWidgetService);
    this.websiteService = Objects.requireNonNull(websiteService);
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.styleService = Objects.requireNonNull(styleService);

  }

  @Cacheable(cacheNames = "widgetsIdsForPage", key = "#a0", sync = true)
  public List<String> getWidgetsIdsForPage(Long pageId) {
    List<WidgetPageDTO> widgetPageDTOS = widgetPageService
      .findByPageId(String.valueOf(pageId));
    return widgetPageDTOS.stream()
      .map(widgetPageDTO -> widgetPageDTO.getWidgetId())
      .collect(Collectors.toList());
  }

  @CacheEvict(cacheNames = "widgetsIdsForPage", key = "#a0")
  public void evictWidgetsIdsForPage(Long pageId) {

  }

  @Cacheable(cacheNames = "asynchronousWidgetForPage", key = "#a0", sync = true)
  public List<String> getAsynchronousWidgetsForPage(Long pageId, List<String> widgetIds) {
    return widgetIds.stream()
      .map(widgetId -> renderingWidgetService.getEntity(Long.parseLong(widgetId)))
      .filter(widget -> widget.isAsynchronous())
      .map(widget -> widget.getName()).collect(Collectors.toList());
  }

  @CacheEvict(cacheNames = "asynchronousWidgetForPage", key = "#a0")
  public void evictAsynchronousWidgetsForPage(Long pageId) {

  }

  @Cacheable(cacheNames = "synchronousWidgetForPage", key = "#a0", sync = true)
  public List<RenderingWidgetDTO> getSynchronousWidgetsForPage(Long pageId,
    List<String> widgetIds) {
    return widgetIds.stream()
      .map(widgetId -> renderingWidgetService.getEntity(Long.parseLong(widgetId)))
      .filter(widget -> !widget.isAsynchronous()).collect(Collectors.toList());
  }

  @CacheEvict(cacheNames = "synchronousWidgetForPage", key = "#a0")
  public void evictSynchronousWidgetForPage(Long pageId) {

  }


  @Cacheable(cacheNames = "news", key = "#a0", sync = true)
  public NewsEntryDTO getNewsEntryById(Long newsEntryId) {
    return newsEntryService.getEntity(newsEntryId);
  }

  @CacheEvict(cacheNames = "news", key = "#a0")
  public void evictNewsEntryById(Long newsEntryId) {

  }

  @Cacheable(cacheNames = "styles", key = "#a0", sync = true)
  public List<StyleDTO> getWebsiteStyles(Long websiteId) {
    List<DesignDTO> designs = designService.findByWebsiteId(websiteId);
    return designs.stream()
      .map(design -> styleService.getEntity(design.getStyleId()))
      .collect(Collectors.toList());
  }

  @CacheEvict(cacheNames = "styles", key = "#a0")
  public void evictWebsiteStyles(Long websiteId) {

  }

  @Cacheable(cacheNames = "websites", key = "#a0", sync = true)
  public WebsiteDTO findWebsiteByName(String websiteName) {
    return websiteService.getWebsiteByName(websiteName);
  }


  @CacheEvict(cacheNames = "websites", key = "#a0")
  public void evictWebsiteByName(String websiteName) {

  }

  @Cacheable(cacheNames = "widgetByName", key = "#a0", sync = true)
  public RenderingWidgetDTO findWidgetByName(String widgetName) {
    return renderingWidgetService.findByName(widgetName);
  }

  @CacheEvict(cacheNames = "widgetByName", key = "#a0")
  public void evictWidgetByName(String widgetName) {

  }

  @Cacheable(cacheNames = "widgetById", key = "#a0", sync = true)
  public RenderingWidgetDTO findWidgetById(Long widgetId) {
    return renderingWidgetService.getEntity(widgetId);
  }

  @CacheEvict(cacheNames = "widgetById", key = "#a0")
  public void evictWidgetById(Long widgetId) {

  }


  @Cacheable(cacheNames = "pagesForWebsiteAndHref", key = "#a0 + '_' + #a1", sync = true)
  public Optional<RenderingPageDTO> getPageForHrefAndWebsite(Long websiteId,
    String pageHref) {
    return sitemapService.findByWebsiteId(websiteId).stream()
      .map(siteMap -> renderingPageService.getEntity(siteMap.getPageId()))
      .filter(page -> page.getHref().equals(pageHref)).findFirst();
  }

  @CacheEvict(cacheNames = "pagesForWebsiteAndHref", key = "#a0 + '_' + #a1")
  public void evictPageForHrefAndWebsite(Long websiteId, String pageHref) {

  }

}
