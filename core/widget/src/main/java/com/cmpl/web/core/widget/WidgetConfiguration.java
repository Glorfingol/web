package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.models.WidgetPage;
import com.cmpl.web.core.widget.page.DefaultWidgetPageDAO;
import com.cmpl.web.core.widget.page.DefaultWidgetPageService;
import com.cmpl.web.core.widget.page.WidgetPageDAO;
import com.cmpl.web.core.widget.page.WidgetPageMapper;
import com.cmpl.web.core.widget.page.WidgetPageRepository;
import com.cmpl.web.core.widget.page.WidgetPageService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {Widget.class, WidgetPage.class})
@EnableJpaRepositories(basePackageClasses = {WidgetRepository.class, WidgetPageRepository.class})
public class WidgetConfiguration {

  @Bean
  public WidgetDAO widgetDAO(WidgetRepository widgetRepository,
    ApplicationEventPublisher publisher) {
    return new DefaultWidgetDAO(widgetRepository, publisher);
  }

  @Bean
  public WidgetMapper widgetMapper() {
    return new WidgetMapper();
  }

  @Bean
  public BackMenuItem widgetBackMenuItem(BackMenuItem webmastering,
    Privilege widgetsReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/widgets").label("back.widgets.label")
      .title("back.widgets.title")
      .iconClass("fa fa-cube").parent(webmastering).order(8)
      .privilege(widgetsReadPrivilege.privilege()).build();
  }

  @Bean
  public WidgetService widgetService(WidgetDAO widgetDAO, WidgetMapper widgetMapper,
    FileService fileService) {
    return new DefaultWidgetService(widgetDAO, widgetMapper, fileService);
  }

  @Bean
  public RenderingWidgetService renderingWidgetService(WidgetDAO widgetDAO,
    RenderingWidgetMapper renderingWidgetMapper) {
    return new DefaultRenderingWidgetService(widgetDAO, renderingWidgetMapper);
  }

  @Bean
  public WidgetPageMapper widgetPageMapper() {
    return new WidgetPageMapper();
  }

  @Bean
  public RenderingWidgetMapper renderingWidgetMapper() {
    return new RenderingWidgetMapper();
  }

  @Bean
  public WidgetPageDAO widgetPageDAO(WidgetPageRepository widgetPageRepository,
    ApplicationEventPublisher publisher) {
    return new DefaultWidgetPageDAO(widgetPageRepository, publisher);
  }

  @Bean
  public WidgetPageService widgetPageService(WidgetPageDAO widgetPageDAO,
    WidgetPageMapper widgetPageMapper) {
    return new DefaultWidgetPageService(widgetPageDAO, widgetPageMapper);
  }

  @Bean
  public WidgetTranslator widgetTranslator() {
    return new DefaultWidgetTranslator();
  }

  @Bean
  public WidgetDispatcher widgetDispatcher(WidgetService widgetService,
    WidgetPageService widgetPageService, WidgetTranslator widgetTranslator) {
    return new DefaultWidgetDispatcher(widgetTranslator, widgetService, widgetPageService
    );
  }

}
