package com.cmpl.web.configuration.manager.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.carousel.CarouselDispatcher;
import com.cmpl.web.core.factory.BackDisplayFactory;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.MenuDispatcher;
import com.cmpl.web.core.news.NewsEntryDispatcher;
import com.cmpl.web.core.page.PageDispatcher;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.manager.ui.core.carousel.CarouselManagerController;
import com.cmpl.web.manager.ui.core.index.IndexManagerController;
import com.cmpl.web.manager.ui.core.login.LoginController;
import com.cmpl.web.manager.ui.core.media.MediaManagerController;
import com.cmpl.web.manager.ui.core.menu.MenuManagerController;
import com.cmpl.web.manager.ui.core.news.NewsManagerController;
import com.cmpl.web.manager.ui.core.page.PageManagerController;
import com.cmpl.web.manager.ui.core.style.StyleManagerController;
import com.cmpl.web.manager.ui.core.user.CurrentUserControllerAdvice;
import com.cmpl.web.manager.ui.core.widget.WidgetManagerController;
import com.cmpl.web.manager.ui.core.widget.WidgetPageManagerController;
import com.cmpl.web.manager.ui.modules.facebook.FacebookController;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;

@Configuration
public class BackControllerConfiguration {

  @Bean
  public FacebookController facebookController(FacebookDisplayFactory facebookDisplayFactory,
      FacebookDispatcher facebookDispatcher) {
    return new FacebookController(facebookDisplayFactory, facebookDispatcher);
  }

  @Bean
  public CarouselManagerController carouselManagerController(CarouselDispatcher carouselDispatcher,
      CarouselManagerDisplayFactory carouselDisplayFactory) {
    return new CarouselManagerController(carouselDispatcher, carouselDisplayFactory);
  }

  @Bean
  public IndexManagerController indexManagerController(BackDisplayFactory loginDisplayFactory) {
    return new IndexManagerController(loginDisplayFactory);
  }

  @Bean
  public LoginController loginController(LoginDisplayFactory loginDisplayFactory) {
    return new LoginController(loginDisplayFactory);
  }

  @Bean
  public MediaManagerController mediaManagerController(MediaService mediaService,
      MediaManagerDisplayFactory mediaManagerDisplayFactory) {
    return new MediaManagerController(mediaService, mediaManagerDisplayFactory);
  }

  @Bean
  public MenuManagerController menuManagerController(MenuDispatcher dispatcher, MenuManagerDisplayFactory displayFactory) {
    return new MenuManagerController(dispatcher, displayFactory);
  }

  @Bean
  public NewsManagerController newsManagerController(NewsManagerDisplayFactory newsManagerDisplayFactory,
      NewsEntryDispatcher dispatcher) {
    return new NewsManagerController(newsManagerDisplayFactory, dispatcher);
  }

  @Bean
  public PageManagerController pageManagerController(PageManagerDisplayFactory pageManagerDisplayFactory,
      PageDispatcher pageDispatcher) {
    return new PageManagerController(pageManagerDisplayFactory, pageDispatcher);
  }

  @Bean
  public StyleManagerController styleManagerController(StyleDisplayFactory displayFactory, StyleDispatcher dispatcher) {
    return new StyleManagerController(displayFactory, dispatcher);
  }

  @Bean
  public WidgetManagerController widgetManagerController(WidgetManagerDisplayFactory widgetManagerDisplayFactory,
      WidgetDispatcher widgetDispatcher) {
    return new WidgetManagerController(widgetManagerDisplayFactory, widgetDispatcher);
  }

  @Bean
  public WidgetPageManagerController widgetPageManagerController(WidgetDispatcher widgetDispatcher) {
    return new WidgetPageManagerController(widgetDispatcher);
  }

  @Bean
  public CurrentUserControllerAdvice currentUserControllerAdvice() {
    return new CurrentUserControllerAdvice();
  }

}
