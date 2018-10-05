package com.cmpl.web.core.factory;

import com.cmpl.web.core.design.DesignDTO;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.news.entry.RenderingNewsEntryDTO;
import com.cmpl.web.core.news.entry.RenderingNewsService;
import com.cmpl.web.core.page.RenderingPageDTO;
import com.cmpl.web.core.page.RenderingPageService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.RenderingStyleDTO;
import com.cmpl.web.core.style.RenderingStyleService;
import com.cmpl.web.core.website.RenderingWebsiteDTO;
import com.cmpl.web.core.website.RenderingWebsiteService;
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

  private final RenderingNewsService renderingNewsService;

  private final WidgetPageService widgetPageService;

  private final RenderingWebsiteService renderingWebsiteService;

  private final SitemapService sitemapService;

  private final DesignService designService;

  private final RenderingStyleService renderingStyleService;

  public DisplayFactoryCacheManager(
    RenderingPageService renderingPageService,
    RenderingNewsService renderingNewsService, WidgetPageService widgetPageService,
    RenderingWidgetService renderingWidgetService,
    RenderingWebsiteService renderingWebsiteService,
    SitemapService sitemapService, DesignService designService,
    RenderingStyleService renderingStyleService) {

    this.renderingPageService = Objects.requireNonNull(renderingPageService);
    this.renderingNewsService = Objects.requireNonNull(renderingNewsService);
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.renderingWidgetService = Objects.requireNonNull(renderingWidgetService);
    this.renderingWebsiteService = Objects.requireNonNull(renderingWebsiteService);
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.renderingStyleService = Objects.requireNonNull(renderingStyleService);

  }

  @Cacheable(cacheNames = "widgetsIdsForPage", key = "#a0", sync = true)
  public List<String> getWidgetsIdsForPage(Long pageId) {
    List<WidgetPageDTO> widgetPageDTOS = widgetPageService
      .findByPageId(pageId);
    return widgetPageDTOS.stream()
      .map(widgetPageDTO -> String.valueOf(widgetPageDTO.getWidgetId()))
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
  public RenderingNewsEntryDTO getNewsEntryById(Long newsEntryId) {
    return renderingNewsService.getEntity(newsEntryId);
  }

  @CacheEvict(cacheNames = "news", key = "#a0")
  public void evictNewsEntryById(Long newsEntryId) {

  }

  @Cacheable(cacheNames = "styles", key = "#a0", sync = true)
  public List<RenderingStyleDTO> getWebsiteStyles(Long websiteId) {
    List<DesignDTO> designs = designService.findByWebsiteId(websiteId);
    return designs.stream()
      .map(design -> renderingStyleService.getEntity(design.getStyleId()))
      .collect(Collectors.toList());
  }

  @CacheEvict(cacheNames = "styles", key = "#a0")
  public void evictWebsiteStyles(Long websiteId) {

  }

  @Cacheable(cacheNames = "websites", key = "#a0", sync = true)
  public RenderingWebsiteDTO findWebsiteByName(String websiteName) {
    return renderingWebsiteService.getWebsiteByName(websiteName);
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
