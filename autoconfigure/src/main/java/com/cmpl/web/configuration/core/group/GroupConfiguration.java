package com.cmpl.web.configuration.core.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.core.events_listeners.GroupEventsListener;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactory;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.Group;
import com.cmpl.web.core.group.GroupDAO;
import com.cmpl.web.core.group.GroupDAOImpl;
import com.cmpl.web.core.group.GroupDispatcher;
import com.cmpl.web.core.group.GroupDispatcherImpl;
import com.cmpl.web.core.group.GroupMapper;
import com.cmpl.web.core.group.GroupRepository;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.group.GroupServiceImpl;
import com.cmpl.web.core.group.GroupTranslator;
import com.cmpl.web.core.group.GroupTranslatorImpl;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EntityScan(basePackageClasses = {Group.class})
@EnableJpaRepositories(basePackageClasses = {GroupRepository.class})
public class GroupConfiguration {

  @Bean
  public GroupMapper groupMapper() {
    return new GroupMapper();
  }

  @Bean
  public GroupDAO groupDAO(ApplicationEventPublisher publisher, GroupRepository groupRepository) {
    return new GroupDAOImpl(groupRepository, publisher);
  }

  @Bean
  public GroupService groupService(GroupDAO groupDAO, GroupMapper groupMapper) {
    return new GroupServiceImpl(groupDAO, groupMapper);
  }

  @Bean
  public BackMenuItem groupBackMenuItem(BackMenuItem administration,
      com.cmpl.web.core.common.user.Privilege groupsReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.groups.href").label("back.groups.label").title("back.groups.title")
        .iconClass("fa fa-building").parent(administration).order(1).privilege(groupsReadPrivilege.privilege()).build();
  }

  @Bean
  public BreadCrumb groupBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_VIEW).build();
  }

  @Bean
  public BreadCrumb groupUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_UPDATE).build();
  }

  @Bean
  public BreadCrumb groupCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_CREATE).build();
  }

  List<BreadCrumbItem> groupBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.groups.title").href("back.groups.href").build());
    return items;
  }

  @Bean
  public GroupTranslator groupTranslator() {
    return new GroupTranslatorImpl();
  }

  @Bean
  public GroupDispatcher groupDispatcher(GroupService groupService, GroupTranslator groupTranslator) {
    return new GroupDispatcherImpl(groupTranslator, groupService);
  }

  @Bean
  public GroupManagerDisplayFactory groupManagerDisplayFactory(GroupService groupService, ContextHolder contextHolder,
      MenuFactory menuFactory, WebMessageSource messageSource,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
      Set<Locale> availableLocales) {
    return new GroupManagerDisplayFactoryImpl(groupService, contextHolder, menuFactory, messageSource,
        breadCrumbRegistry, privileges, availableLocales);
  }

  @Bean
  public GroupEventsListener groupEventsListener() {
    return new GroupEventsListener();
  }

}
