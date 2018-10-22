package com.cmpl.web.configuration.manager.ui;

import com.cmpl.web.core.carousel.CarouselDispatcher;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.design.DesignDispatcher;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactory;
import com.cmpl.web.core.factory.index.IndexDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.factory.website.WebsiteManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.group.GroupDispatcher;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipDispatcher;
import com.cmpl.web.core.news.entry.NewsEntryDispatcher;
import com.cmpl.web.core.page.BackPage;
import com.cmpl.web.core.page.PageDispatcher;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.responsibility.ResponsibilityDispatcher;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.sitemap.SitemapDispatcher;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.user.UserDispatcher;
import com.cmpl.web.core.website.WebsiteDispatcher;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.manager.ui.core.administration.group.GroupManagerController;
import com.cmpl.web.manager.ui.core.administration.memberships.MembershipManagerController;
import com.cmpl.web.manager.ui.core.administration.responsibilities.ResponsibilityManagerController;
import com.cmpl.web.manager.ui.core.administration.role.RoleManagerController;
import com.cmpl.web.manager.ui.core.administration.user.CurrentUserControllerAdvice;
import com.cmpl.web.manager.ui.core.administration.user.UserManagerController;
import com.cmpl.web.manager.ui.core.common.i18n.CurrentLocaleControllerAdvice;
import com.cmpl.web.manager.ui.core.index.IndexManagerController;
import com.cmpl.web.manager.ui.core.login.LoginController;
import com.cmpl.web.manager.ui.core.webmastering.carousel.CarouselManagerController;
import com.cmpl.web.manager.ui.core.webmastering.design.DesignManagerController;
import com.cmpl.web.manager.ui.core.webmastering.media.MediaManagerController;
import com.cmpl.web.manager.ui.core.webmastering.news.NewsManagerController;
import com.cmpl.web.manager.ui.core.webmastering.page.PageManagerController;
import com.cmpl.web.manager.ui.core.webmastering.sitemap.SitemapManagerController;
import com.cmpl.web.manager.ui.core.webmastering.style.StyleManagerController;
import com.cmpl.web.manager.ui.core.webmastering.website.WebsiteManagerController;
import com.cmpl.web.manager.ui.core.webmastering.widget.WidgetManagerController;
import com.cmpl.web.manager.ui.core.webmastering.widget.WidgetPageManagerController;
import com.cmpl.web.manager.ui.modules.facebook.FacebookController;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

@Configuration
public class BackControllerConfiguration {

  @Bean
  public FacebookController facebookController(FacebookDisplayFactory facebookDisplayFactory,
    FacebookDispatcher facebookDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new FacebookController(facebookDisplayFactory, facebookDispatcher, notificationCenter,
      messageSource);
  }

  @Bean
  public CarouselManagerController carouselManagerController(CarouselDispatcher carouselDispatcher,
    CarouselManagerDisplayFactory carouselDisplayFactory, NotificationCenter notificationCenter,
    WebMessageSource messageSource, ContextHolder contextHolder) {
    return new CarouselManagerController(carouselDispatcher, carouselDisplayFactory,
      notificationCenter, messageSource, contextHolder);
  }

  @Bean
  public IndexManagerController indexManagerController(IndexDisplayFactory indexDisplayFactory,
    PluginRegistry<BackPage, String> backPages) {
    return new IndexManagerController(indexDisplayFactory, backPages);
  }

  @Bean
  public LoginController loginController(LoginDisplayFactory loginDisplayFactory,
    UserDispatcher userDispatcher,
    PluginRegistry<BackPage, String> backPages, WebMessageSource messageSource) {
    return new LoginController(loginDisplayFactory, userDispatcher, backPages, messageSource);
  }

  @Bean
  public MediaManagerController mediaManagerController(MediaService mediaService,
    MediaManagerDisplayFactory mediaManagerDisplayFactory, NotificationCenter notificationCenter,
    WebMessageSource messageSource, ContextHolder contextHolder) {
    return new MediaManagerController(mediaService, mediaManagerDisplayFactory, notificationCenter,
      messageSource, contextHolder);
  }

  @Bean
  public NewsManagerController newsManagerController(
    NewsManagerDisplayFactory newsManagerDisplayFactory,
    NewsEntryDispatcher dispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource, ContextHolder contextHolder) {
    return new NewsManagerController(newsManagerDisplayFactory, dispatcher, notificationCenter,
      messageSource, contextHolder);
  }

  @Bean
  public PageManagerController pageManagerController(
    PageManagerDisplayFactory pageManagerDisplayFactory,
    PageDispatcher pageDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new PageManagerController(pageManagerDisplayFactory, pageDispatcher, notificationCenter,
      messageSource);
  }

  @Bean
  public StyleManagerController styleManagerController(StyleDisplayFactory displayFactory,
    StyleDispatcher dispatcher,
    NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new StyleManagerController(displayFactory, dispatcher, notificationCenter,
      messageSource);
  }

  @Bean
  public WidgetManagerController widgetManagerController(
    WidgetManagerDisplayFactory widgetManagerDisplayFactory,
    WidgetDispatcher widgetDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource, PluginRegistry<WidgetProviderPlugin, String> widgetProviders) {
    return new WidgetManagerController(widgetManagerDisplayFactory, widgetDispatcher,
      notificationCenter,
      messageSource, widgetProviders);
  }

  @Bean
  public WidgetPageManagerController widgetPageManagerController(WidgetDispatcher widgetDispatcher,
    NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new WidgetPageManagerController(widgetDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public CurrentUserControllerAdvice currentUserControllerAdvice() {
    return new CurrentUserControllerAdvice();
  }

  @Bean
  public CurrentLocaleControllerAdvice currentLocaleControllerAdvice() {
    return new CurrentLocaleControllerAdvice();
  }


  @Bean
  public UserManagerController userManagerController(
    UserManagerDisplayFactory userManagerDisplayFactory,
    UserDispatcher userDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new UserManagerController(userManagerDisplayFactory, userDispatcher, notificationCenter,
      messageSource);
  }

  @Bean
  public RoleManagerController roleManagerController(
    RoleManagerDisplayFactory roleManagerDisplayFactory,
    RoleDispatcher roleDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new RoleManagerController(roleDispatcher, roleManagerDisplayFactory, notificationCenter,
      messageSource);
  }

  @Bean
  public ResponsibilityManagerController responsibilityManagerController(
    ResponsibilityDispatcher responsibilityDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new ResponsibilityManagerController(responsibilityDispatcher, notificationCenter,
      messageSource);
  }

  @Bean
  public GroupManagerController groupManagerController(
    GroupManagerDisplayFactory groupManagerDisplayFactory,
    GroupDispatcher groupDispatcher, NotificationCenter notificationCenter,
    WebMessageSource messageSource) {
    return new GroupManagerController(groupDispatcher, groupManagerDisplayFactory,
      notificationCenter, messageSource);
  }

  @Bean
  public MembershipManagerController membershipManagerController(
    MembershipDispatcher membershipDispatcher,
    NotificationCenter notificationCenter, WebMessageSource webMessageSource,
    GroupManagerDisplayFactory groupManagerDisplayFactory) {
    return new MembershipManagerController(membershipDispatcher, notificationCenter,
      webMessageSource, groupManagerDisplayFactory);
  }

  @Bean
  public WebsiteManagerController websiteManagerController(WebsiteDispatcher websiteDispatcher,
    NotificationCenter notificationCenter, WebMessageSource messageSource,
    WebsiteManagerDisplayFactory websiteManagerDisplayFactory) {
    return new WebsiteManagerController(websiteDispatcher, websiteManagerDisplayFactory,
      notificationCenter,
      messageSource);
  }

  @Bean
  public DesignManagerController designManagerController(DesignDispatcher designDispatcher,
    NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new DesignManagerController(designDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public SitemapManagerController sitemapManagerController(SitemapDispatcher sitemapDispatcher,
    NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new SitemapManagerController(sitemapDispatcher, notificationCenter, messageSource);
  }
}
