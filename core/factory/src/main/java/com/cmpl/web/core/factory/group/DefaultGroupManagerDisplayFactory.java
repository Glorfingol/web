package com.cmpl.web.core.factory.group;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupCreateForm;
import com.cmpl.web.core.group.GroupDTO;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.group.GroupUpdateForm;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BackPage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class DefaultGroupManagerDisplayFactory extends AbstractBackDisplayFactory<GroupDTO>
  implements GroupManagerDisplayFactory {

  private final GroupService groupService;

  public DefaultGroupManagerDisplayFactory(GroupService groupService, ContextHolder contextHolder,
    MenuFactory menuFactory,
    WebMessageSource messageSource, PluginRegistry<BreadCrumb, String> breadCrumbRegistry,
    Set<Locale> availableLocales,
    MembershipService membershipService, PluginRegistry<BackPage, String> backPagesRegistry) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService,
      membershipService, backPagesRegistry, contextHolder);
    this.groupService = Objects.requireNonNull(groupService);
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllGroups(Locale locale, int pageNumber) {
    BackPage backPage = computeBackPage("GROUP_VIEW");
    ModelAndView groupsManager = super
      .computeModelAndViewForBackPage(backPage, locale);

    PageWrapper<GroupDTO> pagedGroupDTOWrapped = computePageWrapper(locale, pageNumber, "");

    groupsManager.addObject("wrappedEntities", pagedGroupDTOWrapped);

    return groupsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateGroup(Locale locale) {
    BackPage backPage = computeBackPage("GROUP_CREATE");
    ModelAndView groupManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    LOGGER.info("Construction du formulaire de creation des groupes");

    GroupCreateForm form = new GroupCreateForm();

    groupManager.addObject("createForm", form);

    return groupManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateGroup(Locale locale, String groupId) {
    BackPage backPage = computeBackPage("GROUP_UPDATE");
    ModelAndView groupManager = super
      .computeModelAndViewForBackPage(backPage, locale);
    GroupDTO group = groupService.getEntity(Long.parseLong(groupId));
    GroupUpdateForm form = new GroupUpdateForm(group);

    groupManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(group.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) groupManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return groupManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateGroupMain(Locale locale, String groupId) {
    ModelAndView groupManager = new ModelAndView("back/groups/edit/tab_main");
    GroupDTO group = groupService.getEntity(Long.parseLong(groupId));
    GroupUpdateForm form = new GroupUpdateForm(group);

    groupManager.addObject("updateForm", form);

    return groupManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/groups";
  }

  @Override
  protected String getItemLink() {
    return "/manager/groups/";
  }

  @Override
  protected Page<GroupDTO> computeEntries(Locale locale, int pageNumber, String query) {
    List<GroupDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
      Sort.by(Direction.ASC, "name"));
    Page<GroupDTO> pagedGroupDTOEntries;
    if (StringUtils.hasText(query)) {
      pagedGroupDTOEntries = groupService
        .searchEntities(pageRequest,
          query);
    } else {
      pagedGroupDTOEntries = groupService
        .getPagedEntities(pageRequest);
    }
    if (CollectionUtils.isEmpty(pagedGroupDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedGroupDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedGroupDTOEntries.getTotalElements());
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "administration:groups:create";
  }

  @Override
  protected String getCreateItemLink() {
    return getBaseUrl() + "/_create";
  }

  @Override
  protected String getSearchUrl() {
    return "/manager/groups/search";
  }


  @Override
  protected String getSearchPlaceHolder() {
    return "search.groups.placeHolder";
  }
}
