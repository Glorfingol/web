package com.cmpl.core.events_listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.event.UpdatedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Page;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.context.event.EventListener;
import org.thymeleaf.cache.TemplateCacheKey;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class PageEventsListeners {

  private final WidgetPageService widgetPageService;

  private final MembershipService membershipService;

  private final SitemapService sitemapService;

  private final FileService fileService;

  private final Set<Locale> locales;

  private final SpringTemplateEngine templateEngine;

  private static final String HTML_SUFFIX = ".html";

  private static final String FOOTER_SUFFIX = "_footer";

  private static final String META_SUFFIX = "_meta";

  private static final String HEADER_SUFFIX = "_header";

  private static final String LOCALE_CODE_PREFIX = "_";

  private static final String AMP_SUFFIX = "_amp";

  private static final String DECORATOR = "decorator";

  private static final String HEADER = "header";

  private static final String FOOTER = "footer";

  public PageEventsListeners(WidgetPageService widgetPageService, FileService fileService,
    Set<Locale> locales,
    SitemapService sitemapService, MembershipService membershipService,
    SpringTemplateEngine templateEngine) {
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.locales = Objects.requireNonNull(locales);
    this.fileService = Objects.requireNonNull(fileService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.templateEngine = Objects.requireNonNull(templateEngine);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Page) {
      Page deletedPage = (Page) deletedEvent.getEntity();

      if (deletedPage != null) {
        widgetPageService.findByPageId(String.valueOf(deletedPage.getId()))
          .forEach(widgetPageDTO -> widgetPageService.deleteEntity(widgetPageDTO.getId()));
        locales.forEach(locale -> {

          fileService
            .removeFileFromSystem(
              deletedPage.getName() + LOCALE_CODE_PREFIX + locale.getLanguage() + HTML_SUFFIX);
          fileService.removeFileFromSystem(
            deletedPage.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage()
              + HTML_SUFFIX);
          fileService.removeFileFromSystem(
            deletedPage.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage()
              + HTML_SUFFIX);
          fileService.removeFileFromSystem(
            deletedPage.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage()
              + HTML_SUFFIX);
        });

        sitemapService.findByPageId(deletedPage.getId())
          .forEach(sitemap -> sitemapService.deleteEntity(sitemap.getId()));

        membershipService.findByGroupId(deletedPage.getId())
          .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }

    }
  }

  @EventListener
  public void handleUpdatedEvent(UpdatedEvent updatedEvent) {
    if (updatedEvent.getEntity() instanceof Page) {
      Page page = (Page) updatedEvent.getEntity();
      locales.forEach(locale -> computeTemplateCacheKeys(page, locale.getLanguage())
        .forEach(key -> templateEngine.getCacheManager().getTemplateCache().clearKey(key)));
    }
  }

  private List<TemplateCacheKey> computeTemplateCacheKeys(Page page, String localeCode) {
    List<TemplateCacheKey> keys = new ArrayList<>();

    keys.add(
      computeTemplateCacheKey(page.getName() + LOCALE_CODE_PREFIX + localeCode, DECORATOR));

    keys.add(
      computeTemplateCacheKey(page.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + localeCode,
        DECORATOR));

    keys.add(
      computeTemplateCacheKey(page.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + localeCode,
        DECORATOR));

    keys.add(
      computeTemplateCacheKey(page.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + localeCode,
        HEADER));

    keys.add(
      computeTemplateCacheKey(page.getName() + AMP_SUFFIX + LOCALE_CODE_PREFIX + localeCode,
        DECORATOR));

    return keys;
  }

  private TemplateCacheKey computeTemplateCacheKey(String templateName, String owner) {
    return new TemplateCacheKey(owner, templateName, null, 0, 0, null, null);
  }
}
