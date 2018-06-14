package com.cmpl.core.events_listeners;

import java.util.Locale;
import java.util.Set;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.dao.BaseEntity;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.widget.page.WidgetPageService;

public class PageEventsListeners {

  private final WidgetPageService widgetPageService;
  private final FileService fileService;
  private final Set<Locale> locales;

  private static final String HTML_SUFFIX = ".html";
  private static final String FOOTER_SUFFIX = "_footer";
  private static final String META_SUFFIX = "_meta";
  private static final String HEADER_SUFFIX = "_header";
  private static final String LOCALE_CODE_PREFIX = "_";

  public PageEventsListeners(WidgetPageService widgetPageService, FileService fileService, Set<Locale> locales) {
    this.widgetPageService = widgetPageService;
    this.locales = locales;
    this.fileService = fileService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (PageDTO.class.equals(clazz)) {
      Page deletedPage = (Page) deletedEvent.getEntity();

      if (deletedPage != null) {
        widgetPageService.findByPageId(String.valueOf(deletedPage.getId()))
            .forEach(widgetPageDTO -> widgetPageService.deleteEntity(widgetPageDTO.getId()));
        locales.forEach(locale -> {

          fileService
              .removeFileFromSystem(deletedPage.getName() + LOCALE_CODE_PREFIX + locale.getLanguage() + HTML_SUFFIX);
          fileService.removeFileFromSystem(
              deletedPage.getName() + FOOTER_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage() + HTML_SUFFIX);
          fileService.removeFileFromSystem(
              deletedPage.getName() + HEADER_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage() + HTML_SUFFIX);
          fileService.removeFileFromSystem(
              deletedPage.getName() + META_SUFFIX + LOCALE_CODE_PREFIX + locale.getLanguage() + HTML_SUFFIX);
        });
      }

    }
  }

}