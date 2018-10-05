package com.cmpl.core.events.listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.event.UpdatedEvent;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.event.EventListener;
import org.thymeleaf.cache.TemplateCacheKey;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class WidgetEventsListeners {

  private final WidgetPageService widgetPageService;

  private final MembershipService membershipService;

  private final FileService fileService;

  private final SpringTemplateEngine templateEngine;

  private final DisplayFactoryCacheManager displayFactoryCacheManager;

  private final PageService pageService;

  private final Set<Locale> locales;

  private static final String WIDGET_PREFIX = "widget_";

  private static final String HTML_SUFFIX = ".html";

  private static final String LOCALE_CODE_PREFIX = "_";

  private static final String HEADER_SUFFIX = "_header";

  private static final String FOOTER_SUFFIX = "_footer";

  private static final String META_SUFFIX = "_meta";

  private static final String AMP_SUFFIX = "_amp";


  public WidgetEventsListeners(WidgetPageService widgetPageService, FileService fileService,
    Set<Locale> locales, MembershipService membershipService, PageService pageService,
    SpringTemplateEngine templateEngine, DisplayFactoryCacheManager displayFactoryCacheManager) {
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.locales = Objects.requireNonNull(locales);
    this.fileService = Objects.requireNonNull(fileService);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.pageService = Objects.requireNonNull(pageService);
    this.templateEngine = Objects.requireNonNull(templateEngine);
    this.displayFactoryCacheManager = Objects.requireNonNull(displayFactoryCacheManager);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Widget) {
      Widget deletedWidget = (Widget) deletedEvent.getEntity();

      if (deletedWidget != null) {
        widgetPageService.findByWidgetId(deletedWidget.getId())
          .forEach(widgetPageDTO -> widgetPageService.deleteEntity(widgetPageDTO.getId()));
        locales.forEach(locale -> {
          String fileName =
            WIDGET_PREFIX + deletedWidget.getName() + LOCALE_CODE_PREFIX + locale.getLanguage()
              + HTML_SUFFIX;
          fileService.removeFileFromSystem(fileName);
        });

        membershipService.findByGroupId(deletedWidget.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
        displayFactoryCacheManager.evictWidgetByName(deletedWidget.getName());
        displayFactoryCacheManager.evictWidgetById(deletedWidget.getId());
      }


    }

  }

  @EventListener
  public void handleUpdatedEvent(UpdatedEvent updatedEvent) {
    if (updatedEvent.getEntity() instanceof Widget) {
      Widget widget = (Widget) updatedEvent.getEntity();
      locales.forEach(locale -> computeTemplateCacheKeys(widget, locale.getLanguage())
        .forEach(key -> templateEngine.getCacheManager().getTemplateCache().clearKey(key)));

      widgetPageService.findByWidgetId(widget.getId()).forEach(
        widgetPage -> {
          displayFactoryCacheManager
            .evictSynchronousWidgetForPage(Long.valueOf(widgetPage.getPageId()));
          displayFactoryCacheManager
            .evictAsynchronousWidgetsForPage(Long.valueOf(widgetPage.getPageId()));
        });

      displayFactoryCacheManager.evictWidgetByName(widget.getName());
      displayFactoryCacheManager.evictWidgetById(widget.getId());

    }
  }


  private List<TemplateCacheKey> computeTemplateCacheKeys(Widget widget, String localeCode) {
    List<TemplateCacheKey> keys = new ArrayList<>();
    String templateName = WIDGET_PREFIX + widget.getName() + LOCALE_CODE_PREFIX + localeCode;
    keys.add(computeTemplateCacheKey(
      templateName, null));

    if (!widget.isAsynchronous()) {
      List<PageDTO> pages = widgetPageService.findByWidgetId(widget.getId())
        .stream()
        .map(widgetPageDTO -> widgetPageDTO.getPageId()).collect(
          Collectors.toList()).stream().map(id -> pageService.getEntity(Long.valueOf(id)))
        .collect(Collectors.toList());

      pages.forEach(page -> {
        keys.add(
          computeTemplateCacheKey(templateName, page.getName() + LOCALE_CODE_PREFIX + localeCode));
        keys.add(
          computeTemplateCacheKey(templateName,
            page.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + localeCode));
        keys.add(
          computeTemplateCacheKey(templateName,
            page.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + localeCode));
        keys.add(
          computeTemplateCacheKey(templateName,
            page.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + localeCode));
        keys.add(
          computeTemplateCacheKey(templateName,
            page.getName() + AMP_SUFFIX + LOCALE_CODE_PREFIX + localeCode));
      });

    }

    return keys;
  }

  private TemplateCacheKey computeTemplateCacheKey(String templateName, String owner) {
    return new TemplateCacheKey(owner, templateName, null, 0, 0, null, null);
  }
}
