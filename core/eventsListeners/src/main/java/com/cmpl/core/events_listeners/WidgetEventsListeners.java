package com.cmpl.core.events_listeners;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.context.event.EventListener;

public class WidgetEventsListeners {

  private final WidgetPageService widgetPageService;

  private final MembershipService membershipService;

  private final FileService fileService;

  private final Set<Locale> locales;

  private static final String WIDGET_PREFIX = "widget_";

  private static final String HTML_SUFFIX = ".html";

  private static final String LOCALE_CODE_PREFIX = "_";

  public WidgetEventsListeners(WidgetPageService widgetPageService, FileService fileService,
      Set<Locale> locales, MembershipService membershipService) {
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.locales = Objects.requireNonNull(locales);
    this.fileService = Objects.requireNonNull(fileService);
    this.membershipService = Objects.requireNonNull(membershipService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    if (deletedEvent.getEntity() instanceof Widget) {
      Widget deletedWidget = (Widget) deletedEvent.getEntity();

      if (deletedWidget != null) {
        widgetPageService.findByWidgetId(String.valueOf(deletedWidget.getId()))
            .forEach(widgetPageDTO -> widgetPageService.deleteEntity(widgetPageDTO.getId()));
        locales.forEach(locale -> {
          String fileName =
              WIDGET_PREFIX + deletedWidget.getName() + LOCALE_CODE_PREFIX + locale.getLanguage()
                  + HTML_SUFFIX;
          fileService.removeFileFromSystem(fileName);
        });

        membershipService.findByGroupId(deletedWidget.getId())
            .forEach(membershipDTO -> membershipService.deleteEntity(membershipDTO.getId()));
      }
    }

  }
}
