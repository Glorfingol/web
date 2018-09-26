package com.cmpl.web.configuration.core.common;

import com.cmpl.core.events.listeners.DesignEventsListeners;
import com.cmpl.core.events.listeners.GroupEventsListener;
import com.cmpl.core.events.listeners.MediaEventsListeners;
import com.cmpl.core.events.listeners.NewsEventsListeners;
import com.cmpl.core.events.listeners.PageEventsListeners;
import com.cmpl.core.events.listeners.RoleEventsListeners;
import com.cmpl.core.events.listeners.StyleEventsListeners;
import com.cmpl.core.events.listeners.UserEventsListeners;
import com.cmpl.core.events.listeners.WebsiteEventsListeners;
import com.cmpl.core.events.listeners.WidgetEventsListeners;
import com.cmpl.core.events.listeners.WidgetPageEventsListeners;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.DisplayFactoryCacheManager;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

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
    NewsImageService newsImageService, MembershipService membershipService,
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new NewsEventsListeners(newsContentService, newsImageService, membershipService,
      displayFactoryCacheManager);
  }

  @Bean
  public PageEventsListeners pageEventsListener(WidgetPageService widgetPageService,
    FileService fileService,
    Set<Locale> availableLocales, SitemapService sitemapService,
    MembershipService membershipService, SpringTemplateEngine templateEngine,
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new PageEventsListeners(widgetPageService, fileService, availableLocales,
      sitemapService, membershipService, templateEngine, displayFactoryCacheManager);
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
    Set<Locale> availableLocales, MembershipService membershipService, PageService pageService,
    SpringTemplateEngine templateEngine, DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new WidgetEventsListeners(widgetPageService, fileService, availableLocales,
      membershipService, pageService, templateEngine, displayFactoryCacheManager);
  }

  @Bean
  public WebsiteEventsListeners websiteEventsListeners(DesignService designService,
    SitemapService sitemapService, MembershipService membershipService,
    DisplayFactoryCacheManager displayFactoryCacheManager, PageService pageService) {
    return new WebsiteEventsListeners(designService, sitemapService, membershipService, pageService,
      displayFactoryCacheManager);
  }

  @Bean
  public StyleEventsListeners styleEventsListeners(DesignService designService,
    MembershipService membershipService, DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new StyleEventsListeners(designService, membershipService, displayFactoryCacheManager);
  }

  @Bean
  public WidgetPageEventsListeners widgetPageEventsListeners(
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new WidgetPageEventsListeners(displayFactoryCacheManager);
  }

  @Bean
  public DesignEventsListeners designEventsListeners(
    DisplayFactoryCacheManager displayFactoryCacheManager) {
    return new DesignEventsListeners(displayFactoryCacheManager);
  }
}
