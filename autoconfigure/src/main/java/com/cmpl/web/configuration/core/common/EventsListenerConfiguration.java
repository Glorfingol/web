package com.cmpl.web.configuration.core.common;

import com.cmpl.core.events_listeners.GroupEventsListener;
import com.cmpl.core.events_listeners.MediaEventsListeners;
import com.cmpl.core.events_listeners.NewsEventsListeners;
import com.cmpl.core.events_listeners.PageEventsListeners;
import com.cmpl.core.events_listeners.RoleEventsListeners;
import com.cmpl.core.events_listeners.StyleEventsListeners;
import com.cmpl.core.events_listeners.UserEventsListeners;
import com.cmpl.core.events_listeners.WebsiteEventsListeners;
import com.cmpl.core.events_listeners.WidgetEventsListeners;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsListenerConfiguration {

  @Bean
  public GroupEventsListener groupEventsListener(ResponsibilityService responsibilityService,
      MembershipService membershipService) {
    return new GroupEventsListener(responsibilityService, membershipService);
  }

  @Bean
  public MediaEventsListeners mediaEventsListener(FileService fileService,
      MembershipService membershipService) {
    return new MediaEventsListeners(fileService, membershipService);
  }

  @Bean
  public NewsEventsListeners newsEventsListener(NewsContentService newsContentService,
      NewsImageService newsImageService, MembershipService membershipService) {
    return new NewsEventsListeners(newsContentService, newsImageService, membershipService);
  }

  @Bean
  public PageEventsListeners pageEventsListener(WidgetPageService widgetPageService,
      FileService fileService,
      Set<Locale> availableLocales, SitemapService sitemapService,
      MembershipService membershipService) {
    return new PageEventsListeners(widgetPageService, fileService, availableLocales,
        sitemapService, membershipService);
  }

  @Bean
  public RoleEventsListeners roleEventsListener(ResponsibilityService responsibilityService,
      PrivilegeService privilegeService, MembershipService membershipService) {
    return new RoleEventsListeners(responsibilityService, privilegeService, membershipService);
  }

  @Bean
  public UserEventsListeners userEventsListener(ResponsibilityService responsibilityService,
      MembershipService membershipService) {
    return new UserEventsListeners(responsibilityService, membershipService);
  }

  @Bean
  public WidgetEventsListeners widgetEventsListener(WidgetPageService widgetPageService,
      FileService fileService,
      Set<Locale> availableLocales, MembershipService membershipService) {
    return new WidgetEventsListeners(widgetPageService, fileService, availableLocales,
        membershipService);
  }

  @Bean
  public WebsiteEventsListeners websiteEventsListeners(DesignService designService,
      SitemapService sitemapService, MembershipService membershipService) {
    return new WebsiteEventsListeners(designService, sitemapService, membershipService);
  }

  @Bean
  public StyleEventsListeners styleEventsListeners(DesignService designService,
      MembershipService membershipService) {
    return new StyleEventsListeners(designService, membershipService);
  }
}
