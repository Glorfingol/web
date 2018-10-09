package com.cmpl.web.configuration.manager.display;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.carousel.DefaultCarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.group.DefaultGroupManagerDisplayFactory;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactory;
import com.cmpl.web.core.factory.index.DefaultIndexDisplayFactory;
import com.cmpl.web.core.factory.index.IndexDisplayFactory;
import com.cmpl.web.core.factory.login.DefaultLoginDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.media.DefaultMediaManagerDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.menu.DefaultMenuFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.news.DefaultNewsManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.page.DefaultPageManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.role.DefaultRoleManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.style.DefaultStyleDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.user.DefaultUserManagerDisplayFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.factory.website.DefaultWebsiteManagerDisplayFactory;
import com.cmpl.web.core.factory.website.WebsiteManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.DefaultWidgetManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageService;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

@Configuration
public class DisplayFactoryConfiguration {

  @Bean
  public CarouselManagerDisplayFactory carouselManagerDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource, CarouselService carouselService,
    CarouselItemService carouselItemService,
    MediaService mediaService, ContextHolder contextHolder,
    PluginRegistry<BreadCrumb, String> breadCrumbs,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultCarouselManagerDisplayFactory(menuFactory, messageSource, carouselService,
      carouselItemService,
      mediaService, contextHolder, breadCrumbs, availableLocales, groupService,
      membershipService, backPages);
  }

  @Bean
  public GroupManagerDisplayFactory groupManagerDisplayFactory(GroupService groupService,
    ContextHolder contextHolder,
    MenuFactory menuFactory, WebMessageSource messageSource,
    @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales, MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultGroupManagerDisplayFactory(groupService, contextHolder, menuFactory,
      messageSource, breadCrumbRegistry, availableLocales, membershipService,
      backPages);
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    MediaService mediaService, ContextHolder contextHolder,
    PluginRegistry<BreadCrumb, String> breadCrumbs,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultMediaManagerDisplayFactory(menuFactory, messageSource, mediaService,
      contextHolder, breadCrumbs,
      availableLocales, groupService, membershipService, backPages);
  }


  @Bean
  public MenuFactory menuFactory(WebMessageSource messageSource,
    BackMenu backMenu) {
    return new DefaultMenuFactory(messageSource, backMenu);
  }

  @Bean
  public NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder,
    MenuFactory menuFactory,
    WebMessageSource messageSource, NewsEntryService newsEntryService,
    PluginRegistry<BreadCrumb, String> breadCrumbs, Set<Locale> availableLocales,
    GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultNewsManagerDisplayFactory(contextHolder, menuFactory, messageSource,
      newsEntryService, breadCrumbs,
      availableLocales, groupService, membershipService, backPages);
  }

  @Bean
  public PageManagerDisplayFactory pageManagerDisplayFactory(ContextHolder contextHolder,
    MenuFactory menuFactory,
    WebMessageSource messageSource, PageService pageService, WidgetService widgetService,
    WidgetPageService widgetPageService, PluginRegistry<BreadCrumb, String> breadCrumbs,
    Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService,
    WebsiteService websiteService, SitemapService sitemapService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultPageManagerDisplayFactory(menuFactory, messageSource, pageService,
      contextHolder,
      widgetService,
      widgetPageService, breadCrumbs, availableLocales, groupService, membershipService,
      websiteService,
      backPages);
  }

  @Bean
  public RoleManagerDisplayFactory roleManagerDisplayFactory(RoleService roleService,
    PrivilegeService privilegeService,
    ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
    @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultRoleManagerDisplayFactory(roleService, privilegeService, contextHolder,
      menuFactory, messageSource,
      breadCrumbRegistry, privileges, availableLocales, groupService, membershipService,
      backPages);
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    StyleService styleService, ContextHolder contextHolder,
    PluginRegistry<BreadCrumb, String> breadCrumbs,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultStyleDisplayFactory(menuFactory, messageSource, styleService, contextHolder,
      breadCrumbs,
      availableLocales, groupService, membershipService, backPages);
  }

  @Bean
  public UserManagerDisplayFactory userManagerDisplayFactory(UserService userService,
    RoleService roleService,
    ResponsibilityService responsibilityService, ContextHolder contextHolder,
    MenuFactory menuFactory,
    WebMessageSource messageSource, PluginRegistry<BreadCrumb, String> breadCrumbs,
    Set<Locale> availableLocales,
    GroupService groupService, MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultUserManagerDisplayFactory(userService, roleService, responsibilityService,
      contextHolder,
      menuFactory, messageSource, breadCrumbs, availableLocales, groupService, membershipService,
      backPages);
  }

  @Bean
  public WidgetManagerDisplayFactory widgetManagerDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource, WidgetService widgetService, ContextHolder contextHolder,
    PluginRegistry<BreadCrumb, String> breadCrumbs,
    PluginRegistry<WidgetProviderPlugin, String> widgetProviders,
    Set<Locale> availableLocales, GroupService groupService,
    MembershipService membershipService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultWidgetManagerDisplayFactory(menuFactory, messageSource, contextHolder,
      widgetService, breadCrumbs,
      widgetProviders, availableLocales, groupService, membershipService, backPages);
  }

  @Bean
  public LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbs, Set<Locale> availableLocales,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultLoginDisplayFactory(menuFactory, messageSource, breadCrumbs, availableLocales,
      backPages);
  }

  @Bean
  public IndexDisplayFactory indexDisplayFactory(MenuFactory menuFactory,
    WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbs, Set<Locale> availableLocales,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultIndexDisplayFactory(menuFactory, messageSource, breadCrumbs, availableLocales,
      backPages);
  }

  @Bean
  public WebsiteManagerDisplayFactory websiteManagerDisplayFactory(WebsiteService websiteService,
    ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
    PluginRegistry<BreadCrumb, String> breadCrumbs, Set<Locale> availableLocales,
    GroupService groupService,
    MembershipService membershipService,
    PageService pageService, StyleService styleService,
    @Qualifier(value = "backPages") PluginRegistry<BackPage, String> backPages) {
    return new DefaultWebsiteManagerDisplayFactory(menuFactory, messageSource, breadCrumbs,
      availableLocales, groupService,
      membershipService, contextHolder, websiteService,
      pageService, styleService, backPages);
  }


}
